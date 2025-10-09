package com.gomo.app.core.interest.infrastructure.repository;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestRelation;
import com.gomo.app.core.interest.domain.model.RegistrantId;
import com.gomo.app.core.interest.domain.repository.InterestRelationRepository;
import com.gomo.app.core.interest.domain.repository.InterestRepository;
import com.gomo.app.core.interest.fixture.InterestFixture;
import com.gomo.app.core.interest.fixture.InterestRelationFixture;
import com.gomo.app.test.IntegrationTest;

import jakarta.transaction.Transactional;

@DisplayName("[Domain integration]: 관심사 관계선 DB 통합 테스트")
@IntegrationTest
public class InterestRelationRepositoryTest {

	@Autowired
	InterestRelationRepository sut;

	@Autowired
	private InterestRepository interestRepository;

	@Autowired
	private InterestRelationRepository interestRelationRepository;

	private Interest depth1;
	private Interest depth2;
	private Interest depth3;

	@BeforeEach
	public void setUp() {
		RegistrantId registrantId = RegistrantId.of(UUID.randomUUID());
		depth1 = InterestFixture.create(registrantId, "depth1");
		depth2 = InterestFixture.create(registrantId, "depth2");
		depth3 = InterestFixture.create(registrantId, "depth3");
		interestRepository.saveAll(List.of(depth1, depth2, depth3));
	}

	@DisplayName("관심사 관계선은 양방향으로 중복 여부를 확인한다.")
	@Test
	void check_exists() {
		InterestRelation depth1ToDepth2 = InterestRelationFixture.create(depth1, depth2);
		interestRelationRepository.save(depth1ToDepth2);

		boolean parentToChild = sut.existsRelationFor(depth1ToDepth2.parentId(), depth1ToDepth2.childId());
		boolean childToParent = sut.existsRelationFor(depth1ToDepth2.childId(), depth1ToDepth2.parentId());

		assertThat(parentToChild).isTrue();
		assertThat(childToParent).isTrue();
	}

	@DisplayName("특정 관심사가 상위이거나 하위인 모든 관계선을 조회한다.")
	@Test
	void find_all_by_interest_id() {
		InterestRelation depth1ToDepth2 = InterestRelationFixture.create(depth1, depth2);
		InterestRelation depth2ToDepth3 = InterestRelationFixture.create(depth2, depth3);
		interestRelationRepository.saveAll(List.of(depth1ToDepth2, depth2ToDepth3));

		List<InterestRelation> interestRelations = sut.findAllByInterestId(depth2.id());

		assertThat(interestRelations).hasSize(2);
		assertThat(interestRelations).extracting("id")
			.containsExactlyInAnyOrderElementsOf(List.of(depth1ToDepth2.getId(), depth2ToDepth3.getId()));
	}

	@DisplayName("특정 사용자의 관심사 관계를 모두 삭제한다.")
	@Transactional
	@Test
	void delete_all_by_registrant_id() {
		InterestRelation depth1ToDepth2 = InterestRelationFixture.create(depth1, depth2);
		InterestRelation depth2ToDepth3 = InterestRelationFixture.create(depth2, depth3);
		interestRelationRepository.saveAll(List.of(depth1ToDepth2, depth2ToDepth3));

		sut.deleteAllByRegistrantId(depth1.getRegistrantId());

		List<InterestRelation> interestRelations = sut.findAllByRegistrantId(depth1.getRegistrantId());

		assertThat(interestRelations).hasSize(0);
	}
}
