package com.gomo.app.core.interest.domain.service;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.gomo.app.common.arch.DomainService;
import com.gomo.app.core.interest.domain.exception.InterestRelationCycleException;
import com.gomo.app.core.interest.domain.exception.code.InterestRelationErrorCode;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestRelation;
import com.gomo.app.core.interest.domain.repository.InterestRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@DomainService
public class InterestNetworkBuilder {

	private final InterestRepository interestRepository;

	public Map<UUID, Set<UUID>> buildChildIdByParentId(List<InterestRelation> relations) {
		return buildNetwork(relations, InterestRelation::getParentInterestId, InterestRelation::getChildInterestId);
	}

	public Map<UUID, Set<Interest>> buildParentInterestByChildId(List<InterestRelation> relations) {
		if (relations.isEmpty()) {
			return Collections.emptyMap();
		}
		Map<UUID, Interest> interestMap = buildInterestById(relations);
		return buildNetwork(relations, InterestRelation::getChildInterestId, relation -> interestMap.get(relation.getParentInterestId()));
	}

	/**
	 * Builds a network from the given relations.
	 *
	 * @param relations   source data for building the network
	 * @param keyMapper   function to extract the key from a relation
	 * @param valueMapper function to extract values for the value set
	 * @param <K>         key type of the network
	 * @param <V>         value type contained in the value set
	 * @return constructed network
	 */
	private <K, V> Map<K, Set<V>> buildNetwork(List<InterestRelation> relations, Function<InterestRelation, K> keyMapper, Function<InterestRelation, V> valueMapper) {
		Map<K, Set<V>> network = new HashMap<>();
		for (InterestRelation relation : relations) {
			K key = keyMapper.apply(relation);
			V value = valueMapper.apply(relation);

			if (key != null && value != null) {
				network.computeIfAbsent(key, k -> new HashSet<>()).add(value);
			}
		}
		return network;
	}

	private Map<UUID, Interest> buildInterestById(List<InterestRelation> relations) {
		Set<UUID> interestIds = relations.stream()
			.flatMap(r -> Stream.of(r.getParentInterestId(), r.getChildInterestId()))
			.collect(Collectors.toSet());
		return interestRepository.findAllById(interestIds).stream().collect(Collectors.toMap(Interest::getId, Function.identity()));
	}

	public void validateAcyclic(List<InterestRelation> relations, UUID startNode) {
		Map<UUID, Set<UUID>> childIdByParentId = buildChildIdByParentId(relations);
		ArrayDeque<UUID> nodeQueue = new ArrayDeque<>();
		Set<UUID> visitedNodes = new HashSet<>();

		nodeQueue.addLast(startNode);
		while (!nodeQueue.isEmpty()) {
			UUID currentNode = nodeQueue.removeFirst();

			if (isAlreadyVisited(visitedNodes, currentNode)) {
				continue;
			}
			visitedNodes.add(currentNode);

			Set<UUID> neighbors = childIdByParentId.get(currentNode);
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
}
