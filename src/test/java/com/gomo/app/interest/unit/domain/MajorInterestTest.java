package com.gomo.app.interest.unit.domain;

import static com.gomo.app.interest.common.constant.MajorInterestFieldName.*;
import static org.assertj.core.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.common.domain.service.DisplayOrder;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.MajorInterest;
import com.gomo.app.interest.domain.model.MajorInterestId;
import com.gomo.app.interest.domain.model.RegistrantId;
import com.gomo.app.interest.exception.MajorInterestAccessDeniedException;

@DisplayName("[Domain unit]: 주요 관심사 생성 및 수정 테스트")
public class MajorInterestTest {

	private static final MajorInterestId ID = MajorInterestId.of(UUID.randomUUID());
	private static final RegistrantId REGISTRANT_ID = RegistrantId.of(UUID.randomUUID());
	private static final RegistrantId UNAUTHORIZED_ID = RegistrantId.of(UUID.randomUUID());
	private static final InterestId INTEREST_ID = InterestId.of(UUID.randomUUID());
	private static final DisplayOrder DISPLAY_ORDER = DisplayOrder.of(1);

	@DisplayName("주요 관심사를 생성한다.")
	@Test
	void create_major_interest() {
		MajorInterest majorInterest = MajorInterest.of(ID, REGISTRANT_ID, INTEREST_ID, DISPLAY_ORDER);

		assertThat(majorInterest)
			.extracting(ID_FIELD, REGISTRANT_ID_FIELD, INTEREST_ID_FIELD, DISPLAY_ORDER_FIELD)
			.containsExactly(ID, REGISTRANT_ID, INTEREST_ID, DISPLAY_ORDER);
	}

	@DisplayName("주요 관심사 정렬 순서를 변경한다.")
	@Test
	void update_major_interest_with_display_order() {
		MajorInterest majorInterest = MajorInterest.of(ID, REGISTRANT_ID, INTEREST_ID, DISPLAY_ORDER);
		majorInterest.changeOrder(DisplayOrder.of(2));

		assertThat(majorInterest)
			.extracting(ID_FIELD, REGISTRANT_ID_FIELD, INTEREST_ID_FIELD, DISPLAY_ORDER_FIELD)
			.containsExactly(ID, REGISTRANT_ID, INTEREST_ID, DisplayOrder.of(2));
	}

	@DisplayName("주요 관심사는 등록한 사람만 접근할 수 있다.")
	@Test
	void access_major_interest() {
		MajorInterest majorInterest = MajorInterest.of(ID, REGISTRANT_ID, INTEREST_ID, DISPLAY_ORDER);
		majorInterest.validateAuthority(REGISTRANT_ID.getId());
	}

	@DisplayName("주요 관심사는 등록한 사람이 아니면 접근할 수 없다.")
	@Test
	void access_denied_major_interest() {
		MajorInterest majorInterest = MajorInterest.of(ID, REGISTRANT_ID, INTEREST_ID, DISPLAY_ORDER);

		assertThatThrownBy(() -> majorInterest.validateAuthority(UNAUTHORIZED_ID.getId()))
			.isInstanceOf(MajorInterestAccessDeniedException.class)
			.hasMessageContaining("Access denied for the major interest");
	}
}
