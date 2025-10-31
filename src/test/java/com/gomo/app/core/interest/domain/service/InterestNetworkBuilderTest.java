package com.gomo.app.core.interest.domain.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gomo.app.core.interest.domain.exception.InterestRelationCycleException;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestRelation;
import com.gomo.app.core.interest.domain.repository.InterestRepository;
import com.gomo.app.core.interest.fixture.InterestFixture;
import com.gomo.app.core.interest.fixture.InterestRelationFixture;

@DisplayName("[Domain unit]: 관심사 네트워크 서비스 테스트")
@ExtendWith(MockitoExtension.class)
class InterestNetworkBuilderTest {

	@InjectMocks
	private InterestNetworkBuilder sut;

	@Mock
	private InterestRepository interestRepository;

	@Nested
	@DisplayName("관심사 네트워크 생성 테스트: 부모 관심사 id -> 자식 관심사 id 목록")
	class BuildChildIdByParentId {

		@DisplayName("관심사 관계선이 존재할 때, 부모 ID를 Key로 자식 ID Set을 Value로 갖는 그래프를 생성한다.")
		@Test
		void build_network() {
			UUID registrantId = UUID.randomUUID();
			Interest parent1 = InterestFixture.create(registrantId);
			Interest parent2 = InterestFixture.create(registrantId);
			Interest child1 = InterestFixture.create(registrantId);
			Interest child2 = InterestFixture.create(registrantId);
			List<InterestRelation> relations = List.of(
				InterestRelationFixture.create(parent1, child1),
				InterestRelationFixture.create(parent1, child2),
				InterestRelationFixture.create(parent2, child2)
			);

			Map<UUID, Set<UUID>> network = sut.buildChildIdByParentId(relations);

			assertThat(network).hasSize(2);
			assertThat(network.get(parent1.getId())).containsExactlyInAnyOrder(child1.getId(), child2.getId());
			assertThat(network.get(parent2.getId())).containsExactlyInAnyOrder(child2.getId());
			verify(interestRepository, never()).findAllById(any());
		}

		@DisplayName("관심사 관계선이 없으면 빈 그래프를 반환한다.")
		@Test
		void build_empty_network() {
			Map<UUID, Set<UUID>> graph = sut.buildChildIdByParentId(Collections.emptyList());

			assertThat(graph).isEmpty();
		}
	}

	@Nested
	@DisplayName("관심사 네트워크 생성 테스트: 자식 관심사 id -> 부모 관심사 목록")
	class BuildParentInterestByChildId {

		@DisplayName("관심사 관계선이 존재할 때, 자식 ID를 Key로 부모 Interest Set을 Value로 갖는 그래프를 생성한다.")
		@Test
		void build_network() {
			UUID registrantId = UUID.randomUUID();
			Interest parent1 = InterestFixture.create(registrantId, "parent1");
			Interest parent2 = InterestFixture.create(registrantId, "parent2");
			Interest child1 = InterestFixture.create(registrantId, "child1");
			Interest child2 = InterestFixture.create(registrantId, "child2");
			List<InterestRelation> relations = List.of(
				InterestRelationFixture.create(parent1, child1),
				InterestRelationFixture.create(parent2, child1),
				InterestRelationFixture.create(parent2, child2)
			);
			List<Interest> allInterests = List.of(parent1, parent2, child1, child2);
			Set<UUID> allInterestIds = allInterests.stream().map(Interest::getId).collect(Collectors.toSet());
			doReturn(allInterests).when(interestRepository).findAllById(allInterestIds);

			Map<UUID, Set<Interest>> graph = sut.buildParentInterestByChildId(relations);

			assertThat(graph).hasSize(2);
			assertThat(graph.get(child1.getId())).containsExactlyInAnyOrder(parent1, parent2);
			assertThat(graph.get(child2.getId())).containsExactlyInAnyOrder(parent2);
			verify(interestRepository, times(1)).findAllById(any());
		}

		@DisplayName("관심사 관계선이 없으면 빈 그래프를 반환하고, 불필요한 관심사 조회를 하지 않는다.")
		@Test
		void build_empty_network() {
			Map<UUID, Set<Interest>> graph = sut.buildParentInterestByChildId(Collections.emptyList());

			assertThat(graph).isEmpty();
			verify(interestRepository, never()).findAllById(any());
		}
	}

	@Nested
	@DisplayName("네트워크 순환 구조 검증 테스트")
	class ValidateAcyclic {

		@DisplayName("순환이 없는 그래프는 예외를 발생시키지 않는다.")
		@Test
		void validate_not_acyclic_graph() {
			Interest depth1 = InterestFixture.create();
			Interest depth2 = InterestFixture.create();
			Interest depth3 = InterestFixture.create();
			InterestRelation depth1ToDepth2 = InterestRelationFixture.create(depth1, depth2);
			InterestRelation depth2ToDepth3 = InterestRelationFixture.create(depth2, depth3);
			List<InterestRelation> relations = List.of(depth1ToDepth2, depth2ToDepth3);

			assertThatCode(() -> sut.validateAcyclic(relations, depth1.getId())).doesNotThrowAnyException();
		}

		@DisplayName("직접적인 순환(A -> B, B -> A)이 존재하면 예외를 발생시킨다.")
		@Test
		void validate_direct_acyclic_graph() {
			Interest depth1 = InterestFixture.create();
			Interest depth2 = InterestFixture.create();
			InterestRelation depth1ToDepth2 = InterestRelationFixture.create(depth1, depth2);
			InterestRelation depth2ToDepth1 = InterestRelationFixture.create(depth2, depth1);
			List<InterestRelation> relations = List.of(depth1ToDepth2, depth2ToDepth1);

			assertThatThrownBy(() -> sut.validateAcyclic(relations, depth1.getId())).isInstanceOf(InterestRelationCycleException.class);
		}

		@DisplayName("간접적인 순환(A -> B -> C -> A)이 존재하면 예외를 발생시킨다.")
		@Test
		void validate_indirect_acyclic_graph() {
			Interest depth1 = InterestFixture.create();
			Interest depth2 = InterestFixture.create();
			Interest depth3 = InterestFixture.create();
			InterestRelation depth1ToDepth2 = InterestRelationFixture.create(depth1, depth2);
			InterestRelation depth2ToDepth3 = InterestRelationFixture.create(depth2, depth3);
			InterestRelation depth3ToDepth1 = InterestRelationFixture.create(depth3, depth1);
			List<InterestRelation> relations = List.of(depth1ToDepth2, depth2ToDepth3, depth3ToDepth1);

			assertThatThrownBy(() -> sut.validateAcyclic(relations, depth1.getId())).isInstanceOf(InterestRelationCycleException.class);
		}

		@DisplayName("자기 자신을 참조하는 순환(A -> A)이 존재하면 예외를 발생시킨다.")
		@Test
		void validate_self_acyclic_graph() {
			Interest depth1 = InterestFixture.create();
			List<InterestRelation> relations = List.of(InterestRelationFixture.create(depth1, depth1));

			assertThatThrownBy(() -> sut.validateAcyclic(relations, depth1.getId())).isInstanceOf(InterestRelationCycleException.class);
		}

		@DisplayName("시작 노드에서 도달할 수 없는 곳은 순환이 있어도 예외를 발생시키지 않는다.")
		@Test
		void validate_acyclic_graph_doesnt_reach_from_start() {
			Interest depth1 = InterestFixture.create();
			Interest depth2 = InterestFixture.create();
			Interest depth3 = InterestFixture.create();
			Interest depth4 = InterestFixture.create();
			InterestRelation depth1ToDepth2 = InterestRelationFixture.create(depth1, depth2);
			InterestRelation depth3ToDepth4 = InterestRelationFixture.create(depth3, depth4);
			InterestRelation depth4ToDepth3 = InterestRelationFixture.create(depth4, depth3);
			List<InterestRelation> relations = List.of(depth1ToDepth2, depth3ToDepth4, depth4ToDepth3);

			assertThatCode(() -> sut.validateAcyclic(relations, depth1.getId())).doesNotThrowAnyException();
		}
	}
}
