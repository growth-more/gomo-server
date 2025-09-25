package com.gomo.app.core.streak.fixture;

import java.util.UUID;

import com.gomo.app.core.streak.domain.model.Achiever;
import com.gomo.app.core.streak.domain.model.AchieverId;

public class AchieverFixture {

	public static Achiever achiever() {
		return new Achiever(
			AchieverId.of(UUID.randomUUID()),
			0,
			0
		);
	}

	public static Achiever achiever(UUID achieverId) {
		return new Achiever(
			AchieverId.of(achieverId),
			0,
			0
		);
	}

	public static Achiever achiever(int currentStreakDays, int longestStreakDays) {
		return new Achiever(
			AchieverId.of(UUID.randomUUID()),
			currentStreakDays,
			longestStreakDays
		);
	}
}
