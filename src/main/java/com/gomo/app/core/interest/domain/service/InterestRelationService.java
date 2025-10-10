package com.gomo.app.core.interest.domain.service;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.DomainService;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.core.interest.domain.model.ChildInterestId;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestId;
import com.gomo.app.core.interest.domain.model.InterestRelation;
import com.gomo.app.core.interest.domain.model.InterestRelationId;
import com.gomo.app.core.interest.domain.model.ParentInterestId;
import com.gomo.app.core.interest.domain.model.RegistrantId;
import com.gomo.app.core.interest.domain.repository.InterestRelationRepository;
import com.gomo.app.core.interest.exception.InterestRelationCycleException;
import com.gomo.app.core.interest.exception.InterestRelationDuplicatedException;
import com.gomo.app.core.interest.exception.InterestRelationNotFoundException;
import com.gomo.app.core.interest.exception.code.InterestRelationErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class InterestRelationService {

	private final ProficiencyService proficiencyService;
	private final InterestRelationRepository interestRelationRepository;

	@Transactional
	public InterestRelation create(RegistrantId registrantId, Interest parentInterest, Interest childInterest) {
		ParentInterestId parentInterestId = ParentInterestId.of(parentInterest.getId());
		ChildInterestId childInterestId = ChildInterestId.of(childInterest.getId());

		ensureNotDuplicated(parentInterestId, childInterestId);
		InterestRelation interestRelation = InterestRelation.of(
			InterestRelationId.of(UUIDGenerator.generate()),
			registrantId,
			parentInterestId,
			childInterestId
		);
		ensureDoesNotCreateCycle(registrantId, interestRelation);

		enhanceProficiencyOfParentInterests(parentInterest, childInterest);
		return interestRelationRepository.save(interestRelation);
	}

	public InterestRelation find(InterestRelationId interestRelationId) {
		return interestRelationRepository.findById(interestRelationId)
			.orElseThrow(() -> new InterestRelationNotFoundException(InterestRelationErrorCode.NOT_FOUND));
	}

	public List<InterestRelation> findAllByInterestId(UUID interestId) {
		return interestRelationRepository.findAllByInterestId(interestId);
	}

	@Transactional
	public void delete(InterestRelation interestRelation, Interest parentInterest, Interest childInterest) {
		reduceProficiencyOfParentInterests(parentInterest, childInterest);
		interestRelationRepository.delete(interestRelation);
	}

	private void ensureNotDuplicated(ParentInterestId parentInterestId, ChildInterestId childInterestId) {
		if (interestRelationRepository.existsRelationFor(parentInterestId.getId(), childInterestId.getId())) {
			throw new InterestRelationDuplicatedException(InterestRelationErrorCode.DUPLICATED);
		}
	}

	private void ensureDoesNotCreateCycle(RegistrantId registrantId, InterestRelation proposedRelation) {
		Map<InterestId, Set<InterestId>> interestGraph = buildInterestGraph(registrantId, proposedRelation);
		InterestId startNode = proposedRelation.getParentInterestId().toInterestId();
		ensureAcyclicGraph(startNode, interestGraph);
	}

	@NotNull
	private Map<InterestId, Set<InterestId>> buildInterestGraph(RegistrantId registrantId, InterestRelation proposedRelation) {
		List<InterestRelation> existingRelations = interestRelationRepository.findAllByRegistrantId(registrantId);
		existingRelations.add(proposedRelation);

		Map<InterestId, Set<InterestId>> interestGraph = new HashMap<>();
		for (InterestRelation relation : existingRelations) {
			InterestId parentId = relation.getParentInterestId().toInterestId();
			InterestId childId = relation.getChildInterestId().toInterestId();
			interestGraph.computeIfAbsent(parentId, k -> new HashSet<>()).add(childId);
		}

		return interestGraph;
	}

	private void ensureAcyclicGraph(InterestId startNode, Map<InterestId, Set<InterestId>> interestGraph) {
		ArrayDeque<InterestId> nodeQueue = new ArrayDeque<>();
		Set<InterestId> visitedNodes = new HashSet<>();

		nodeQueue.addLast(startNode);
		while (!nodeQueue.isEmpty()) {
			InterestId currentNode = nodeQueue.removeFirst();

			if (isAlreadyVisited(visitedNodes, currentNode)) {
				continue;
			}
			visitedNodes.add(currentNode);

			Set<InterestId> neighbors = interestGraph.get(currentNode);
			if (neighbors != null) {
				for (InterestId neighbor : neighbors) {
					if (isCycleReturningToStart(neighbor, startNode)) {
						throw new InterestRelationCycleException(InterestRelationErrorCode.UNEXPECTED_CYCLE);
					}

					if (isAlreadyVisited(visitedNodes, neighbor)) {
						continue;
					}
					nodeQueue.addLast(neighbor);
				}
			}
		}
	}

	private boolean isCycleReturningToStart(InterestId neighbor, InterestId startNode) {
		return neighbor.equals(startNode);
	}

	private boolean isAlreadyVisited(Set<InterestId> visitedNodes, InterestId currentNode) {
		return visitedNodes.contains(currentNode);
	}

	private void enhanceProficiencyOfParentInterests(Interest parentInterest, Interest childInterest) {
		proficiencyService.adjust(parentInterest, childInterest.getProficiency().getTotalScore());
	}

	private void reduceProficiencyOfParentInterests(Interest parentInterest, Interest childInterest) {
		proficiencyService.adjust(parentInterest, -1 * childInterest.getProficiency().getTotalScore());
	}
}
