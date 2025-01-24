package com.gomo.app.streak.presentation.response;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;

@Getter
public class ReadStreakResponse {

	private UUID id;
	private LocalDateTime filledDateTime;
	private int weekOfYear;
	private int completedQuestCount;

	private ReadStreakResponse(
		UUID id,
		LocalDateTime filledDateTime,
		int weekOfYear,
		int completedQuestCount
	) {
		this.id = id;
		this.filledDateTime = filledDateTime;
		this.weekOfYear = weekOfYear;
		this.completedQuestCount = completedQuestCount;
	}

	public static ReadStreakResponse of(
		UUID id,
		LocalDateTime filledDateTime,
		int weekOfYear,
		int completedQuestCount
	) {
		return new ReadStreakResponse(id, filledDateTime, weekOfYear, completedQuestCount);
	}
}
