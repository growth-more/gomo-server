package com.gomo.app.interest.common.fixture;

import java.util.UUID;

import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestId;
import com.gomo.app.interest.domain.model.InterestName;
import com.gomo.app.interest.domain.model.RegistrantId;

public class InterestFixture {

	public static Interest interest() {
		return Interest.of(
			InterestId.of(UUID.randomUUID()),
			RegistrantId.of(UUID.randomUUID()),
			InterestName.of("Interest Name"),
			"https://image.nurdykim.me/gomo/interest-logo.png"
		);
	}

	public static Interest interest(RegistrantId registrantId) {
		return Interest.of(
			InterestId.of(UUID.randomUUID()),
			registrantId,
			InterestName.of("Interest Name"),
			"https://image.nurdykim.me/gomo/interest-logo.png"
		);
	}

	public static Interest interest(InterestId id, String name) {
		return Interest.of(
			id,
			RegistrantId.of(UUID.randomUUID()),
			InterestName.of(name),
			"https://image.nurdykim.me/gomo/interest-logo.png"
		);
	}

	public static Interest defaultLogo() {
		return Interest.of(
			InterestId.of(UUID.randomUUID()),
			RegistrantId.of(UUID.randomUUID()),
			InterestName.of("Interest Name"),
			null
		);
	}
}
