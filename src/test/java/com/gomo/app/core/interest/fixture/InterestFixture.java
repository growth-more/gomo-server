package com.gomo.app.core.interest.fixture;

import java.util.UUID;

import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.InterestName;
import com.gomo.app.core.interest.domain.model.Level;
import com.gomo.app.core.interest.domain.model.Logo;
import com.gomo.app.core.interest.domain.model.Proficiency;
import com.gomo.app.core.interest.domain.model.Score;

public class InterestFixture {

	public static Interest create() {
		return Interest.of(
			UUID.randomUUID(),
			UUID.randomUUID(),
			InterestName.of("Interest Name"),
			Logo.of("https://logo_url"),
			"000000"
		);
	}

	public static Interest create(UUID registrantId) {
		return Interest.of(
			UUID.randomUUID(),
			registrantId,
			InterestName.of("Interest Name"),
			Logo.of("https://logo_url"),
			"000000"
		);
	}

	public static Interest create(UUID registrantId, String name) {
		return Interest.of(
			UUID.randomUUID(),
			registrantId,
			InterestName.of(name),
			Logo.of("https://logo_url"),
			"#FF0000"
		);
	}

	public static Interest create(UUID registrantId, String name, int proficiencyScore) {
		return new Interest(
			UUID.randomUUID(),
			registrantId,
			new Proficiency(Level.createDefault(), Score.of(proficiencyScore), proficiencyScore),
			InterestName.of(name),
			Logo.of("https://logo_url"),
			"000000"
		);
	}

	public static Interest create(UUID id, UUID registrantId, String name) {
		return Interest.of(
			id,
			registrantId,
			InterestName.of(name),
			Logo.of("https://logo_url"),
			"000000"
		);
	}

	public static Interest create(Logo logo) {
		return Interest.of(
			UUID.randomUUID(),
			UUID.randomUUID(),
			InterestName.of("Interest Name"),
			logo,
			"000000"
		);
	}
}
