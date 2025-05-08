package com.gomo.app.interest.fixture;

import java.util.UUID;

import com.gomo.app.displayorder.DisplayOrder;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.MajorInterest;
import com.gomo.app.interest.domain.model.MajorInterestId;
import com.gomo.app.interest.domain.model.RegistrantId;

public class MajorInterestFixture {

	public static MajorInterest majorInterest() {
		return MajorInterest.of(
			MajorInterestId.of(UUID.randomUUID()),
			RegistrantId.of(UUID.randomUUID()),
			InterestId.of(UUID.randomUUID()),
			DisplayOrder.of(1)
		);
	}

	public static MajorInterest majorInterest(int displayOrder) {
		return MajorInterest.of(
			MajorInterestId.of(UUID.randomUUID()),
			RegistrantId.of(UUID.randomUUID()),
			InterestId.of(UUID.randomUUID()),
			DisplayOrder.of(displayOrder)
		);
	}

	public static MajorInterest majorInterest(RegistrantId registrantId, InterestId interestId) {
		return MajorInterest.of(
			MajorInterestId.of(UUID.randomUUID()),
			registrantId,
			interestId,
			DisplayOrder.of(1)
		);
	}

	public static MajorInterest majorInterest(RegistrantId registrantId, InterestId interestId, int displayOrder) {
		return MajorInterest.of(
			MajorInterestId.of(UUID.randomUUID()),
			registrantId,
			interestId,
			DisplayOrder.of(displayOrder)
		);
	}
}
