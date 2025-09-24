package com.gomo.app.interest.fixture;

import java.util.UUID;

import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestId;
import com.gomo.app.core.interest.domain.model.InterestName;
import com.gomo.app.core.interest.domain.model.Level;
import com.gomo.app.core.interest.domain.model.Logo;
import com.gomo.app.core.interest.domain.model.Proficiency;
import com.gomo.app.core.interest.domain.model.RegistrantId;
import com.gomo.app.core.interest.domain.model.Score;

public class InterestFixture {

	public static Interest create() {
		return Interest.of(
			InterestId.of(UUID.randomUUID()),
			RegistrantId.of(UUID.randomUUID()),
			InterestName.of("Interest Name"),
			Logo.of("https://logo_url"),
			"000000"
		);
	}

	public static Interest create(RegistrantId registrantId) {
		return Interest.of(
			InterestId.of(UUID.randomUUID()),
			registrantId,
			InterestName.of("Interest Name"),
			Logo.of("https://logo_url"),
			"000000"
		);
	}

	public static Interest create(RegistrantId registrantId, String name) {
		return Interest.of(
			InterestId.of(UUID.randomUUID()),
			registrantId,
			InterestName.of(name),
			Logo.of("https://logo_url"),
			"#FF0000"
		);
	}

	public static Interest create(RegistrantId registrantId, String name, int proficiencyScore) {
		return new Interest(
			InterestId.of(UUID.randomUUID()),
			registrantId,
			new Proficiency(Level.createDefault(), Score.of(proficiencyScore), proficiencyScore),
			InterestName.of(name),
			Logo.of("https://logo_url"),
			"000000"
		);
	}

	public static Interest create(InterestId id, String name) {
		return Interest.of(
			id,
			RegistrantId.of(UUID.randomUUID()),
			InterestName.of(name),
			Logo.of("https://logo_url"),
			"000000"
		);
	}

	public static Interest create(Logo logo) {
		return Interest.of(
			InterestId.of(UUID.randomUUID()),
			RegistrantId.of(UUID.randomUUID()),
			InterestName.of("Interest Name"),
			logo,
			"000000"
		);
	}
}
