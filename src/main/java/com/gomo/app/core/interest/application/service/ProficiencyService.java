package com.gomo.app.core.interest.application.service;

import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.interest.application.port.in.ProficiencyPropagator;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestRelation;
import com.gomo.app.core.interest.domain.repository.InterestRelationRepository;
import com.gomo.app.core.interest.domain.service.InterestNetworkBuilder;
import com.gomo.app.core.interest.domain.service.ProficiencyCalculator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class ProficiencyService implements ProficiencyPropagator {

	private final InterestService interestService;
	private final InterestRelationRepository interestRelationRepository;
	private final InterestNetworkBuilder interestNetworkBuilder;
	private final ProficiencyCalculator proficiencyCalculator;

	@Override
	public void propagate(UUID interestId, int deltaScore) {
		Interest interest = interestService.readById(interestId);
		List<InterestRelation> relations = interestRelationRepository.findAllByRegistrantId(interest.registrantId());
		Map<UUID, Set<Interest>> parentInterestByChildId = interestNetworkBuilder.buildParentInterestByChildId(relations);
		Set<UUID> adjustSuccessIds = new HashSet<>();
		ArrayDeque<Interest> candidateInterests = new ArrayDeque<>();
		candidateInterests.addLast(interest);
		while (!candidateInterests.isEmpty()) {
			Interest current = candidateInterests.removeFirst();
			if (adjustSuccessIds.contains(current.getId())) {
				continue;
			}
			adjustProficiency(current, deltaScore, adjustSuccessIds);
			Set<Interest> parents = parentInterestByChildId.get(current.getId());
			enqueueParents(parents, candidateInterests, adjustSuccessIds);
		}
	}

	private void adjustProficiency(Interest interest, int deltaTotalScore, Set<UUID> adjustSuccessIds) {
		interest.adjustProficiency(deltaTotalScore, proficiencyCalculator);
		adjustSuccessIds.add(interest.getId());
	}

	private void enqueueParents(Set<Interest> parents, ArrayDeque<Interest> queue, Set<UUID> adjustSuccessIds) {
		if (parents == null || parents.isEmpty()) {
			return;
		}
		for (Interest parent : parents) {
			if (!adjustSuccessIds.contains(parent.getId())) {
				queue.addLast(parent);
			}
		}
	}
}
