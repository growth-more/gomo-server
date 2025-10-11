package com.gomo.app.core.interest.fixture;

import java.util.UUID;

import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestRelation;

public class InterestRelationFixture {

	public static InterestRelation create() {
		return InterestRelation.of(
			UUID.randomUUID(),
			UUID.randomUUID(),
			UUID.randomUUID(),
			UUID.randomUUID()
		);
	}

	public static InterestRelation create(Interest parent, Interest child) {
		return InterestRelation.of(
			UUID.randomUUID(),
			parent.getRegistrantId(),
			parent.getId(),
			child.getId()
		);
	}

	public static InterestRelation create(UUID registrantId, Interest parent, Interest child) {
		return InterestRelation.of(
			UUID.randomUUID(),
			registrantId,
			parent.getId(),
			child.getId()
		);
	}
}
