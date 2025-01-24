package com.gomo.app.interest.common.fixture.majorinterest;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.gomo.app.common.domain.DisplayOrder;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.MajorInterest;
import com.gomo.app.interest.domain.model.MajorInterestId;
import com.gomo.app.interest.domain.model.RegistrantId;

/**
 * 주요 관심사 픽스처
 * 실제 데이터베이스에 존재하는 테스트 레코드와 동일한 값을 수동으로 지정해서 사용한다.
 */
@Component
public class JavaMajorInterestFixture {

	private static final String ID = "172916b7-d7f6-11ef-b7fd-0bd5a273f618";
	private static final String REGISTRANT_ID = "a10581ce-d721-11ef-a8a5-2508e2a6438b";
	private static final String INTEREST_ID = "f8c51811-d7c5-11ef-82dc-4322ccc3e338";
	private static final int DISPLAY_ORDER = 1;

	public static MajorInterest java() {
		return MajorInterest.of(
			MajorInterestId.of(UUID.fromString(ID)),
			RegistrantId.of(UUID.fromString(REGISTRANT_ID)),
			InterestId.of(UUID.fromString(INTEREST_ID)),
			DisplayOrder.of(DISPLAY_ORDER)
		);
	}

	public static String id() {
		return ID;
	}

	public static String registrantId() {
		return REGISTRANT_ID;
	}

	public static String interestId() {
		return INTEREST_ID;
	}

	public static int displayOrder() {
		return DISPLAY_ORDER;
	}
}
