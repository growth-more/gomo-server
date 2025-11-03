package com.gomo.app.core.interest.domain.model;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.core.interest.domain.exception.InterestRelationAccessDeniedException;

@DisplayName("[Domain unit]: 관심사 관계 생성 및 접근 테스트")
public class InterestRelationTest {

	private static final UUID ID = UUID.randomUUID();
	private static final UUID REGISTRANT_ID = UUID.randomUUID();
	private static final UUID NOT_REGISTRANT_ID_PARAM = UUID.randomUUID();
	private static final UUID PARENT_INTEREST_ID = UUID.randomUUID();
	private static final UUID CHILD_INTEREST_ID = UUID.randomUUID();

	@DisplayName("관심사 관계를 생성한다.")
	@Test
	void create_interest_relation() {
		InterestRelation interestRelation = InterestRelation.of(ID, REGISTRANT_ID, PARENT_INTEREST_ID, CHILD_INTEREST_ID);

		assertThat(interestRelation)
			.extracting("id", "registrantId", "parentInterestId", "childInterestId")
			.containsExactly(ID, REGISTRANT_ID, PARENT_INTEREST_ID, CHILD_INTEREST_ID);
	}

	@DisplayName("관심사 관계는 등록한 사람만 접근할 수 있다.")
	@Test
	void access_interest_relation() {
		InterestRelation interestRelation = InterestRelation.of(ID, REGISTRANT_ID, PARENT_INTEREST_ID, CHILD_INTEREST_ID);
		interestRelation.validateAuthority(REGISTRANT_ID);
	}

	@DisplayName("관심사 관계를 등록한 사람이 아니면 접근할 수 없다.")
	@Test
	void access_denied_interest_relation() {
		InterestRelation interestRelation = InterestRelation.of(ID, REGISTRANT_ID, PARENT_INTEREST_ID, CHILD_INTEREST_ID);

		assertThatThrownBy(() -> interestRelation.validateAuthority(NOT_REGISTRANT_ID_PARAM))
			.isInstanceOf(InterestRelationAccessDeniedException.class)
			.hasMessageContaining("Access denied for the interest relation");
	}
}
