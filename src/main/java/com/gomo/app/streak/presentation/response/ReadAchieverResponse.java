package com.gomo.app.streak.presentation.response;

import java.util.UUID;

import com.gomo.app.streak.domain.model.Achiever;

import lombok.Getter;

@Getter
public class ReadAchieverResponse {

	private UUID id;
	private int longestStreakDays;
	private int currentStreakDays;

	private ReadAchieverResponse(
		UUID id,
		int longestStreakDays,
		int currentStreakDays
	) {
		this.id = id;
		this.longestStreakDays = longestStreakDays;
		this.currentStreakDays = currentStreakDays;
	}

	public static ReadAchieverResponse of(Achiever achiever) {
		return new ReadAchieverResponse(
			achiever.getId().getId(),
			achiever.getLongestStreakDays(),
			achiever.getCurrentStreakDays()
		);
	}
}
