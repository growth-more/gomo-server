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
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestRelation;
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
	public InterestRelation create(UUID registrantId, Interest parentInterest, Interest childInterest) {
		UUID parentInterestId = parentInterest.getId();
		UUID childInterestId = childInterest.getId();

		ensureNotDuplicated(parentInterestId, childInterestId);
		InterestRelation interestRelation = InterestRelation.of(
			UUIDGenerator.generate(),
			registrantId,
			parentInterestId,
			childInterestId
		);
		ensureDoesNotCreateCycle(registrantId, interestRelation);

		enhanceProficiencyOfParentInterests(parentInterest, childInterest);
		return interestRelationRepository.save(interestRelation);
	}

	public InterestRelation find(UUID interestRelationId) {
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

	private void ensureNotDuplicated(UUID parentInterestId, UUID childInterestId) {
		if (interestRelationRepository.existsRelationFor(parentInterestId, childInterestId)) {
			throw new InterestRelationDuplicatedException(InterestRelationErrorCode.DUPLICATED);
		}
	}

	private void ensureDoesNotCreateCycle(UUID registrantId, InterestRelation proposedRelation) {
		Map<UUID, Set<UUID>> interestGraph = buildInterestGraph(registrantId, proposedRelation);
		UUID startNode = proposedRelation.getParentInterestId();
		ensureAcyclicGraph(startNode, interestGraph);
	}

	@NotNull
	private Map<UUID, Set<UUID>> buildInterestGraph(UUID registrantId, InterestRelation proposedRelation) {
		List<InterestRelation> existingRelations = interestRelationRepository.findAllByRegistrantId(registrantId);
		existingRelations.add(proposedRelation);

		Map<UUID, Set<UUID>> interestGraph = new HashMap<>();
		for (InterestRelation relation : existingRelations) {
			UUID parentId = relation.getParentInterestId();
			UUID childId = relation.getChildInterestId();
			interestGraph.computeIfAbsent(parentId, k -> new HashSet<>()).add(childId);
		}

		return interestGraph;
	}

	private void ensureAcyclicGraph(UUID startNode, Map<UUID, Set<UUID>> interestGraph) {
		ArrayDeque<UUID> nodeQueue = new ArrayDeque<>();
		Set<UUID> visitedNodes = new HashSet<>();

		nodeQueue.addLast(startNode);
		while (!nodeQueue.isEmpty()) {
			UUID currentNode = nodeQueue.removeFirst();

			if (isAlreadyVisited(visitedNodes, currentNode)) {
				continue;
			}
			visitedNodes.add(currentNode);

			Set<UUID> neighbors = interestGraph.get(currentNode);
			if (neighbors != null) {
				for (UUID neighbor : neighbors) {
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

	private boolean isCycleReturningToStart(UUID neighbor, UUID startNode) {
		return neighbor.equals(startNode);
	}

	private boolean isAlreadyVisited(Set<UUID> visitedNodes, UUID currentNode) {
		return visitedNodes.contains(currentNode);
	}

	private void enhanceProficiencyOfParentInterests(Interest parentInterest, Interest childInterest) {
		proficiencyService.adjust(parentInterest, childInterest.getProficiency().getTotalScore());
	}

	private void reduceProficiencyOfParentInterests(Interest parentInterest, Interest childInterest) {
		proficiencyService.adjust(parentInterest, -1 * childInterest.getProficiency().getTotalScore());
	}
}
