package com.gomo.app.core.interest.domain.service;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.DomainService;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestRelation;
import com.gomo.app.core.interest.domain.model.LevelThresholdPolicy;
import com.gomo.app.core.interest.domain.model.ProficiencyCalculator;
import com.gomo.app.core.interest.domain.repository.InterestRelationRepository;
import com.gomo.app.core.interest.domain.repository.InterestRepository;
import com.gomo.app.core.interest.domain.repository.LevelThresholdPolicyRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class ProficiencyService {

	private final InterestRepository interestRepository;
	private final InterestRelationRepository interestRelationRepository;
	private final LevelThresholdPolicyRepository levelThresholdPolicyRepository;
	private ProficiencyCalculator proficiencyCalculator;

	@PostConstruct
	protected void initialize() {
		List<LevelThresholdPolicy> levelThresholdPolicies = levelThresholdPolicyRepository.findAll();
		proficiencyCalculator = ProficiencyCalculator.from(levelThresholdPolicies);
	}

	@Transactional
	public void adjust(Interest interest, int deltaTotalScore) {
		Map<UUID, Set<Interest>> childToParentMap = buildChildToParentMap(interest.getRegistrantId());

		Set<UUID> adjustSuccessIds = new HashSet<>();
		ArrayDeque<Interest> candidateInterests = new ArrayDeque<>();
		candidateInterests.addLast(interest);
		while (!candidateInterests.isEmpty()) {
			Interest current = candidateInterests.removeFirst();
			if (alreadyAdjustedInterest(current.getId(), adjustSuccessIds)) {
				continue;
			}

			adjustProficiency(current, deltaTotalScore, adjustSuccessIds, proficiencyCalculator);
			enqueueParents(childToParentMap.get(current.getId()), candidateInterests, adjustSuccessIds);
		}
	}

	private void adjustProficiency(Interest interest, int deltaTotalScore, Set<UUID> adjustSuccessIds, ProficiencyCalculator proficiencyCalculator) {
		interest.adjustProficiency(deltaTotalScore, proficiencyCalculator);
		adjustSuccessIds.add(interest.getId());
	}

	private void enqueueParents(Set<Interest> parents, ArrayDeque<Interest> queue, Set<UUID> adjustSuccessIds) {
		if (existParentInterests(parents)) {
			for (Interest parent : parents) {
				if (!alreadyAdjustedInterest(parent.getId(), adjustSuccessIds)) {
					queue.addLast(parent);
				}
			}
		}
	}

	private boolean existParentInterests(Set<Interest> parents) {
		return parents != null;
	}

	private boolean alreadyAdjustedInterest(UUID currentId, Set<UUID> adjustSuccessIds) {
		return adjustSuccessIds.contains(currentId);
	}

	private Map<UUID, Set<Interest>> buildChildToParentMap(UUID registrantId) {
		List<InterestRelation> allRelations = interestRelationRepository.findAllByRegistrantId(registrantId);

		Set<UUID> interestIds = new HashSet<>();
		for (InterestRelation relation : allRelations) {
			interestIds.add(relation.getChildInterestId());
			interestIds.add(relation.getParentInterestId());
		}

		List<Interest> interests = interestRepository.findAllById(interestIds);
		Map<UUID, Interest> interestMap = new HashMap<>();
		for (Interest interest : interests) {
			interestMap.put(interest.getId(), interest);
		}

		Map<UUID, Set<Interest>> childToParentMap = new HashMap<>();
		for (InterestRelation relation : allRelations) {
			UUID childId = relation.getChildInterestId();
			UUID parentId = relation.getParentInterestId();
			childToParentMap.computeIfAbsent(childId, k -> new HashSet<>()).add(interestMap.get(parentId));
		}

		return childToParentMap;
	}
}
