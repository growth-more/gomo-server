package com.gomo.app.core.interest.fixture;

import java.util.UUID;

import com.gomo.app.common.displayorder.DisplayOrder;
import com.gomo.app.core.interest.domain.model.InterestId;
import com.gomo.app.core.interest.domain.model.MajorInterest;
import com.gomo.app.core.interest.domain.model.MajorInterestId;
import com.gomo.app.core.interest.domain.model.RegistrantId;

public class MajorInterestFixture {

	public static MajorInterest create() {
		return MajorInterest.of(
			MajorInterestId.of(UUID.randomUUID()),
			RegistrantId.of(UUID.randomUUID()),
			InterestId.of(UUID.randomUUID()),
			DisplayOrder.of(1)
		);
	}

	public static MajorInterest create(int displayOrder) {
		return MajorInterest.of(
			MajorInterestId.of(UUID.randomUUID()),
			RegistrantId.of(UUID.randomUUID()),
			InterestId.of(UUID.randomUUID()),
			DisplayOrder.of(displayOrder)
		);
	}

	public static MajorInterest create(RegistrantId registrantId, InterestId interestId) {
		return MajorInterest.of(
			MajorInterestId.of(UUID.randomUUID()),
			registrantId,
			interestId,
			DisplayOrder.of(1)
		);
	}

	public static MajorInterest create(RegistrantId registrantId, InterestId interestId, int displayOrder) {
		return MajorInterest.of(
			MajorInterestId.of(UUID.randomUUID()),
			registrantId,
			interestId,
			DisplayOrder.of(displayOrder)
		);
	}
}
