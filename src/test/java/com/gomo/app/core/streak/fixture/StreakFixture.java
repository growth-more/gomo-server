package com.gomo.app.core.streak.fixture;

import java.time.LocalDate;
import java.util.UUID;

import com.gomo.app.core.streak.domain.model.AchieverId;
import com.gomo.app.core.streak.domain.model.Streak;
import com.gomo.app.core.streak.domain.model.StreakId;
import com.gomo.app.core.streak.domain.model.StreakType;

public class StreakFixture {

	public static Streak streak() {
		return Streak.of(
			StreakId.of(UUID.randomUUID()),
			AchieverId.of(UUID.randomUUID()),
			StreakType.DAILY,
			LocalDate.of(2025, 2, 5),
			1
		);
	}

	public static Streak streak(StreakType type) {
		return Streak.of(
			StreakId.of(UUID.randomUUID()),
			AchieverId.of(UUID.randomUUID()),
			type,
			LocalDate.of(2025, 2, 5),
			1
		);
	}

	public static Streak streak(int completedQuestCount) {
		return Streak.of(
			StreakId.of(UUID.randomUUID()),
			AchieverId.of(UUID.randomUUID()),
			StreakType.DAILY,
			LocalDate.of(2025, 2, 5),
			completedQuestCount
		);
	}

	public static Streak streak(UUID achieverId, StreakType type, LocalDate filledDate) {
		return Streak.of(
			StreakId.of(UUID.randomUUID()),
			AchieverId.of(achieverId),
			type,
			filledDate,
			1
		);
	}
}
