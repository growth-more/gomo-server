package com.gomo.app.core.streak.application.port.dto;

import java.time.LocalDate;
import java.util.UUID;

import com.gomo.app.core.streak.domain.model.Streak;

public record StreakDto(UUID id, String streakType, LocalDate filledDate, int completedQuestCount) {

	public static StreakDto from(Streak streak) {
		return new StreakDto(streak.id(), streak.getStreakType().name(), streak.getFilledDate(), streak.getCompletedQuestCount());
	}
}
