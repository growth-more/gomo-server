package com.gomo.app.streak.presentation.response;

import java.util.List;

import com.gomo.app.streak.domain.model.StreakType;

import lombok.Getter;

@Getter
public class ListStreakResponse {

	private StreakType streakType;
	private List<ReadStreakResponse> streaks;

	private ListStreakResponse(
		StreakType streakType,
		List<ReadStreakResponse> streaks
	) {
		this.streakType = streakType;
		this.streaks = streaks;
	}

	public static ListStreakResponse of(
		StreakType streakType,
		List<ReadStreakResponse> streaks
	) {
		return new ListStreakResponse(streakType, streaks);
	}
}
