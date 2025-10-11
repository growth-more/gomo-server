package com.gomo.app.core.streak.fixture;

import java.time.LocalDate;
import java.util.UUID;

import com.gomo.app.core.streak.domain.model.Streak;
import com.gomo.app.core.streak.domain.model.StreakType;

public class StreakFixture {

	public static Streak create() {
		return Streak.of(
			UUID.randomUUID(),
			UUID.randomUUID(),
			StreakType.DAILY,
			LocalDate.of(2025, 2, 5),
			1
		);
	}

	public static Streak create(StreakType type) {
		return Streak.of(
			UUID.randomUUID(),
			UUID.randomUUID(),
			type,
			LocalDate.of(2025, 2, 5),
			1
		);
	}

	public static Streak create(int completedQuestCount) {
		return Streak.of(
			UUID.randomUUID(),
			UUID.randomUUID(),
			StreakType.DAILY,
			LocalDate.of(2025, 2, 5),
			completedQuestCount
		);
	}

	public static Streak create(UUID achieverId, StreakType type, LocalDate filledDate) {
		return Streak.of(
			UUID.randomUUID(),
			achieverId,
			type,
			filledDate,
			1
		);
	}
}
