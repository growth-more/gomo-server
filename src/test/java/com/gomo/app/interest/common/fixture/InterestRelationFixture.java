package com.gomo.app.interest.common.fixture;

import java.util.UUID;

import com.gomo.app.interest.domain.model.ChildInterestId;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.InterestRelation;
import com.gomo.app.interest.domain.model.InterestRelationId;
import com.gomo.app.interest.domain.model.ParentInterestId;
import com.gomo.app.interest.domain.model.RegistrantId;

public class InterestRelationFixture {

	public static InterestRelation relation() {
		return InterestRelation.of(
			InterestRelationId.of(UUID.randomUUID()),
			RegistrantId.of(UUID.randomUUID()),
			ParentInterestId.of(InterestId.of(UUID.randomUUID())),
			ChildInterestId.of(InterestId.of(UUID.randomUUID()))
		);
	}
}
