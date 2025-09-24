package com.gomo.app.core.interest.domain.service;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.DomainService;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestId;
import com.gomo.app.core.interest.domain.model.InterestRelation;
import com.gomo.app.core.interest.domain.policy.InMemoryScoreThresholdPolicyProvider;
import com.gomo.app.core.interest.domain.repository.InterestRelationRepository;
import com.gomo.app.core.interest.domain.repository.InterestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class ProficiencyService {

	private final InMemoryScoreThresholdPolicyProvider policyProvider;
	private final InterestService interestService;
	private final InterestRepository interestRepository;
	private final InterestRelationRepository interestRelationRepository;

	@Transactional
	public void adjust(InterestId interestId, int deltaTotalScore) {
		int[] totalScoreForLevel = policyProvider.getTotalScoreForLevel();
		int[] scoreThresholdPerLevel = policyProvider.getScoreThresholdPerLevel();
		Map<InterestId, Set<Interest>> childToParentMap = buildChildToParentMap();

		Set<InterestId> enhancedIds = new HashSet<>();
		ArrayDeque<Interest> queue = new ArrayDeque<>();
		Interest interest = interestService.find(interestId);
		queue.addLast(interest);
		while (!queue.isEmpty()) {
			Interest current = queue.removeFirst();
			if (alreadyEnhancedInterest(current.getId(), enhancedIds)) {
				continue;
			}

			adjustProficiency(current, deltaTotalScore, enhancedIds, totalScoreForLevel, scoreThresholdPerLevel);
			enqueueParents(childToParentMap.get(current.getId()), queue, enhancedIds);
		}
	}

	private void adjustProficiency(Interest interest, int deltaTotalScore, Set<InterestId> enhancedIds, int[] totalScoreForLevel, int[] scoreThresholdPerLevel) {
		interest.adjustProficiency(deltaTotalScore, totalScoreForLevel, scoreThresholdPerLevel);
		enhancedIds.add(interest.getId());
	}

	private void enqueueParents(Set<Interest> parents, ArrayDeque<Interest> queue, Set<InterestId> enhancedIds) {
		if (existParentInterests(parents)) {
			for (Interest parent : parents) {
				if (!alreadyEnhancedInterest(parent.getId(), enhancedIds)) {
					queue.addLast(parent);
				}
			}
		}
	}

	private boolean existParentInterests(Set<Interest> parents) {
		return parents != null;
	}

	private boolean alreadyEnhancedInterest(InterestId currentId, Set<InterestId> enhancedIds) {
		return enhancedIds.contains(currentId);
	}

	private Map<InterestId, Set<Interest>> buildChildToParentMap() {
		List<InterestRelation> allRelations = interestRelationRepository.findAll();

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
