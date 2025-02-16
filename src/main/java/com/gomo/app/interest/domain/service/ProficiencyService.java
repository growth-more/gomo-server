package com.gomo.app.interest.domain.service;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.domain.service.DomainService;
import com.gomo.app.common.exception.DomainErrorCode;
import com.gomo.app.common.exception.NotFoundException;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.InterestRelation;
import com.gomo.app.interest.domain.repository.InterestRelationRepository;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.exception.InterestErrorCode;
import com.gomo.app.interest.exception.ProficiencyAdjustFailureException;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class ProficiencyService {

	private final ScoreThresholdPolicyService scoreThresholdPolicyService;
	private final InterestRepository interestRepository;
	private final InterestRelationRepository interestRelationRepository;

	@Transactional
	@Retryable(
		value = ObjectOptimisticLockingFailureException.class,
		backoff = @Backoff(delay = 100)
	)
	public void adjust(InterestId interestId, int deltaTotalScore) {
		int[] totalScoreForLevel = scoreThresholdPolicyService.getTotalScoreForLevel();
		int[] scoreThresholdForLevel = scoreThresholdPolicyService.getScoreThresholdPolicy();
		Map<InterestId, Set<Interest>> childToParentMap = buildChildToParentMap();

		Set<InterestId> enhancedIds = new HashSet<>();
		ArrayDeque<Interest> queue = new ArrayDeque<>();
		queue.addLast(findInterest(interestId));
		while (!queue.isEmpty()) {
			Interest current = queue.removeFirst();
			if (alreadyEnhancedInterest(current.getId(), enhancedIds)) {
				continue;
			}

			adjustProficiency(current, deltaTotalScore, enhancedIds, totalScoreForLevel, scoreThresholdForLevel);
			enqueueParents(childToParentMap.get(current.getId()), queue, enhancedIds);
		}
	}

	private void adjustProficiency(Interest interest, int deltaTotalScore, Set<InterestId> enhancedIds, int[] totalScoreForLevel, int[] scoreThresholdForLevel) {
		interest.adjustProficiency(deltaTotalScore, totalScoreForLevel, scoreThresholdForLevel);
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

	private Interest findInterest(InterestId interestId) {
		return interestRepository.findById(interestId)
			.orElseThrow(() -> new NotFoundException(DomainErrorCode.NOT_FOUND, "Interest not found with id: " + interestId));
	}

	@Recover
	protected void handleOptimisticLockingFailure(ObjectOptimisticLockingFailureException e) {
		throw new ProficiencyAdjustFailureException(InterestErrorCode.PROFICIENCY_ENHANCEMENT_CONFLICT, e);
	}
}
