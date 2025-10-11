package com.gomo.app.core.streak.fixture;

import java.util.UUID;

import com.gomo.app.core.streak.domain.model.Achiever;

public class AchieverFixture {

	public static Achiever create() {
		return new Achiever(
			UUID.randomUUID(),
			0,
			0
		);
	}

	public static Achiever create(UUID achieverId) {
		return new Achiever(
			achieverId,
			0,
			0
		);
	}

	public static Achiever create(int currentStreakDays, int longestStreakDays) {
		return new Achiever(
			UUID.randomUUID(),
			currentStreakDays,
			longestStreakDays
		);
	}
}
