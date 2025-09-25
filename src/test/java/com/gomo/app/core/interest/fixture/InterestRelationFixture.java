package com.gomo.app.core.interest.fixture;

import java.util.UUID;

import com.gomo.app.core.interest.domain.model.ChildInterestId;
import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestId;
import com.gomo.app.core.interest.domain.model.InterestRelation;
import com.gomo.app.core.interest.domain.model.InterestRelationId;
import com.gomo.app.core.interest.domain.model.ParentInterestId;
import com.gomo.app.core.interest.domain.model.RegistrantId;

public class InterestRelationFixture {

	public static InterestRelation create() {
		return InterestRelation.of(
			InterestRelationId.of(UUID.randomUUID()),
			RegistrantId.of(UUID.randomUUID()),
			ParentInterestId.of(InterestId.of(UUID.randomUUID())),
			ChildInterestId.of(InterestId.of(UUID.randomUUID()))
		);
	}

	public static InterestRelation create(Interest parent, Interest child) {
		return InterestRelation.of(
			InterestRelationId.of(UUID.randomUUID()),
			parent.getRegistrantId(),
			ParentInterestId.of(parent.getId()),
			ChildInterestId.of(child.getId())
		);
	}

	public static InterestRelation create(RegistrantId registrantId, Interest parent, Interest child) {
		return InterestRelation.of(
			InterestRelationId.of(UUID.randomUUID()),
			registrantId,
			ParentInterestId.of(parent.getId()),
			ChildInterestId.of(child.getId())
		);
	}
}
