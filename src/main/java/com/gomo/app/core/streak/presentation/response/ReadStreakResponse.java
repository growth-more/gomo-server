package com.gomo.app.core.streak.presentation.response;

import java.time.LocalDate;
import java.util.UUID;

import com.gomo.app.core.streak.application.port.dto.StreakDto;

import lombok.Getter;

@Getter
public class ReadStreakResponse {

	private final UUID id;
	private final String streakType;
	private final LocalDate filledDate;
	private final int completedQuestCount;

	private ReadStreakResponse(UUID id, String streakType, LocalDate filledDate, int completedQuestCount) {
		this.id = id;
		this.streakType = streakType;
		this.filledDate = filledDate;
		this.completedQuestCount = completedQuestCount;
	}

	public static ReadStreakResponse from(StreakDto dto) {
		return new ReadStreakResponse(dto.id(), dto.streakType(), dto.filledDate(), dto.completedQuestCount());
	}
}
