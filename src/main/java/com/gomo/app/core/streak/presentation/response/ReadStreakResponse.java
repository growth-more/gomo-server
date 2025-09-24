package com.gomo.app.core.streak.presentation.response;

import java.time.LocalDate;
import java.util.UUID;

import com.gomo.app.core.streak.domain.model.Streak;
import com.gomo.app.core.streak.domain.model.StreakType;

import lombok.Getter;

@Getter
public class ReadStreakResponse {

	private UUID id;
	private StreakType streakType;
	private LocalDate filledDate;
	private int completedQuestCount;

	private ReadStreakResponse(
		UUID id,
		StreakType streakType,
		LocalDate filledDate,
		int completedQuestCount
	) {
		this.id = id;
		this.streakType = streakType;
		this.filledDate = filledDate;
		this.completedQuestCount = completedQuestCount;
	}

	public static ReadStreakResponse of(Streak streak) {
		return new ReadStreakResponse(
			streak.getId().getId(),
			streak.getStreakType(),
			streak.getFilledDate(),
			streak.getCompletedQuestCount());
	}
}
