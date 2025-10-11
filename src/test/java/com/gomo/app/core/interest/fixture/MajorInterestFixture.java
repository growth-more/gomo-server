package com.gomo.app.core.interest.fixture;

import java.util.UUID;

import com.gomo.app.common.displayorder.DisplayOrder;
import com.gomo.app.core.interest.domain.model.MajorInterest;

public class MajorInterestFixture {

	public static MajorInterest create() {
		return MajorInterest.of(
			UUID.randomUUID(),
			UUID.randomUUID(),
			UUID.randomUUID(),
			DisplayOrder.of(1)
		);
	}

	public static MajorInterest create(int displayOrder) {
		return MajorInterest.of(
			UUID.randomUUID(),
			UUID.randomUUID(),
			UUID.randomUUID(),
			DisplayOrder.of(displayOrder)
		);
	}

	public static MajorInterest create(UUID registrantId, UUID interestId) {
		return MajorInterest.of(
			UUID.randomUUID(),
			registrantId,
			interestId,
			DisplayOrder.of(1)
		);
	}

	public static MajorInterest create(UUID registrantId, UUID interestId, int displayOrder) {
		return MajorInterest.of(
			UUID.randomUUID(),
			registrantId,
			interestId,
			DisplayOrder.of(displayOrder)
		);
	}
}
