package com.gomo.app.core.streak.presentation.response;

import java.util.UUID;

import com.gomo.app.core.streak.application.port.dto.AchieverDto;

import lombok.Getter;

@Getter
public class ReadAchieverResponse {

	private final UUID id;
	private final int longestStreakDays;
	private final int currentStreakDays;

	private ReadAchieverResponse(UUID id, int longestStreakDays, int currentStreakDays) {
		this.id = id;
		this.longestStreakDays = longestStreakDays;
		this.currentStreakDays = currentStreakDays;
	}

	public static ReadAchieverResponse from(AchieverDto dto) {
		return new ReadAchieverResponse(dto.id(), dto.longestStreakDays(), dto.currentStreakDays());
	}
}
