package com.gomo.app.core.interest.domain.service;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.DomainService;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestId;
import com.gomo.app.core.interest.domain.model.InterestRelation;
import com.gomo.app.core.interest.domain.model.ProficiencyPolicies;
import com.gomo.app.core.interest.domain.model.RegistrantId;
import com.gomo.app.core.interest.domain.repository.InterestRelationRepository;
import com.gomo.app.core.interest.domain.repository.InterestRepository;
import com.gomo.app.core.interest.domain.repository.ProficiencyPolicyRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class ProficiencyService {

	private final ProficiencyPolicyRepository proficiencyPolicyRepository;
	private final InterestRepository interestRepository;
	private final InterestRelationRepository interestRelationRepository;

	@Transactional
	public void adjust(Interest interest, int deltaTotalScore) {
		ProficiencyPolicies proficiencyPolicies = proficiencyPolicyRepository.getPolicies();
		Map<InterestId, Set<Interest>> childToParentMap = buildChildToParentMap(interest.getRegistrantId());

		Set<InterestId> adjustedIds = new HashSet<>();
		ArrayDeque<Interest> candidateInterests = new ArrayDeque<>();
		candidateInterests.addLast(interest);
		while (!candidateInterests.isEmpty()) {
			Interest current = candidateInterests.removeFirst();
			if (alreadyAdjustedInterest(current.getId(), adjustedIds)) {
				continue;
			}

			adjustProficiency(current, deltaTotalScore, adjustedIds, proficiencyPolicies);
			enqueueParents(childToParentMap.get(current.getId()), candidateInterests, adjustedIds);
		}
	}

	private void adjustProficiency(Interest interest, int deltaTotalScore, Set<InterestId> adjustedIds, ProficiencyPolicies proficiencyPolicies) {
		interest.adjustProficiency(deltaTotalScore, proficiencyPolicies);
		adjustedIds.add(interest.getId());
	}

	private void enqueueParents(Set<Interest> parents, ArrayDeque<Interest> queue, Set<InterestId> adjustedIds) {
		if (existParentInterests(parents)) {
			for (Interest parent : parents) {
				if (!alreadyAdjustedInterest(parent.getId(), adjustedIds)) {
					queue.addLast(parent);
				}
			}
		}
	}

	private boolean existParentInterests(Set<Interest> parents) {
		return parents != null;
	}

	private boolean alreadyAdjustedInterest(InterestId currentId, Set<InterestId> adjustedIds) {
		return adjustedIds.contains(currentId);
	}

	private Map<InterestId, Set<Interest>> buildChildToParentMap(RegistrantId registrantId) {
		List<InterestRelation> allRelations = interestRelationRepository.findAllByRegistrantId(registrantId);

		Set<InterestId> interestIds = new HashSet<>();
		for (InterestRelation relation : allRelations) {
			interestIds.add(relation.getChildInterestId().toInterestId());
			interestIds.add(relation.getParentInterestId().toInterestId());
		}

		List<Interest> interests = interestRepository.findAllById(interestIds);
		Map<InterestId, Interest> interestMap = new HashMap<>();
		for (Interest interest : interests) {
			interestMap.put(interest.getId(), interest);
		}

		Map<InterestId, Set<Interest>> childToParentMap = new HashMap<>();
		for (InterestRelation relation : allRelations) {
			InterestId childId = relation.getChildInterestId().toInterestId();
			InterestId parentId = relation.getParentInterestId().toInterestId();
			childToParentMap.computeIfAbsent(childId, k -> new HashSet<>()).add(interestMap.get(parentId));
		}

		return childToParentMap;
	}
}
