package com.gomo.app.interest.domain.service;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.DomainService;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.interest.domain.model.ChildInterestId;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.InterestRelation;
import com.gomo.app.interest.domain.model.InterestRelationId;
import com.gomo.app.interest.domain.model.ParentInterestId;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.interest.domain.repository.InterestRelationRepository;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.exception.InterestNotFoundException;
import com.gomo.app.interest.exception.InterestRelationCycleException;
import com.gomo.app.interest.exception.InterestRelationDuplicatedException;
import com.gomo.app.interest.exception.InterestRelationNotFoundException;
import com.gomo.app.interest.exception.code.InterestErrorCode;
import com.gomo.app.interest.exception.code.InterestRelationErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class InterestRelationService {

	private final ProficiencyService proficiencyService;
	private final InterestRelationRepository interestRelationRepository;
	private final InterestRepository interestRepository;

	@Transactional
	public InterestRelation create(RegistrantId registrantId, ParentInterestId parentInterestId, ChildInterestId childInterestId) {
		ensureNotDuplicated(parentInterestId, childInterestId);

		InterestRelation interestRelation = InterestRelation.of(
			InterestRelationId.of(UUIDGenerator.generate()),
			registrantId,
			parentInterestId,
			childInterestId
		);
		ensureDoesNotCreateCycle(registrantId, interestRelation);

		enhanceProficiencyOfParentInterests(parentInterestId, childInterestId);
		return interestRelationRepository.save(interestRelation);
	}

	@Transactional
	public void delete(UUID accessorId, InterestRelationId interestRelationId) {
		InterestRelation interestRelation = interestRelationRepository.findById(interestRelationId)
			.orElseThrow(() -> new InterestRelationNotFoundException(InterestRelationErrorCode.NOT_FOUND));
		interestRelation.validateAuthority(accessorId);

		reduceProficiencyOfParentInterests(interestRelation.getParentInterestId(), interestRelation.getChildInterestId());
		interestRelationRepository.delete(interestRelation);
	}

	private void ensureNotDuplicated(ParentInterestId parentInterestId, ChildInterestId childInterestId) {
		if(interestRelationRepository.existsRelationFor(parentInterestId.getId(), childInterestId.getId())) {
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

	private void enhanceProficiencyOfParentInterests(ParentInterestId parentInterestId, ChildInterestId childInterestId) {
		Interest childInterest = findInterest(childInterestId);
		proficiencyService.adjust(parentInterestId.toInterestId(), childInterest.getProficiency().getTotalScore());
	}

	private void reduceProficiencyOfParentInterests(ParentInterestId parentInterestId, ChildInterestId childInterestId) {
		Interest childInterest = findInterest(childInterestId);
		proficiencyService.adjust(parentInterestId.toInterestId(), -1 * childInterest.getProficiency().getTotalScore());
	}

	private Interest findInterest(ChildInterestId childInterestId) {
		return interestRepository.findById(childInterestId.toInterestId())
			.orElseThrow(() -> new InterestNotFoundException(InterestErrorCode.NOT_FOUND));
	}
}