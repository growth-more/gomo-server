package com.gomo.app.interest.common.fixture.interestrelation;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.gomo.app.interest.domain.model.ChildInterestId;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.InterestRelation;
import com.gomo.app.interest.domain.model.InterestRelationId;
import com.gomo.app.interest.domain.model.ParentInterestId;
import com.gomo.app.interest.domain.model.RegistrantId;

/**
 * 테스트 샘플 관심사 간선
 * 실제 데이터베이스에 존재하는 테스트 레코드와 동일한 값을 수동으로 지정해서 사용한다.
 */
//
@Component
public class FirstToParentInterestRelationFixture {

	private static final String ID = "d08d1d8d-d7eb-11ef-9ea7-ffd9d039bbca";
	private static final String REGISTRANT_ID = "a10581ce-d721-11ef-a8a5-2508e2a6438b";
	private static final String PARENT_INTEREST_ID = "3bd1b3f7-d7c6-11ef-abb8-a7e09b2a499c";
	private static final String CHILD_INTEREST_ID = "90a387a7-d7c5-11ef-b4d7-079c7dc41274";

	public static InterestRelation relation() {
		return InterestRelation.of(
			InterestRelationId.of(UUID.fromString(ID)),
			RegistrantId.of(UUID.fromString(REGISTRANT_ID)),
			ParentInterestId.of(InterestId.of(UUID.fromString(PARENT_INTEREST_ID))),
			ChildInterestId.of(InterestId.of(UUID.fromString(CHILD_INTEREST_ID)))
		);
	}

	public static String id() {
		return ID;
	}

	public static String registrantId() {
		return REGISTRANT_ID;
	}

	public static String parentInterestId() {
		return PARENT_INTEREST_ID;
	}

	public static String childInterestId() {
		return CHILD_INTEREST_ID;
	}
}
