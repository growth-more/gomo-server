package com.gomo.app.streak.presentation.response;

import java.util.List;

import lombok.Getter;

@Getter
public class ListStreakResponse {

	private List<ReadStreakResponse> dailyStreaks;
	private List<ReadStreakResponse> weeklyStreaks;
	private List<ReadStreakResponse> monthlyStreaks;

	private ListStreakResponse(
		List<ReadStreakResponse> dailyStreaks,
		List<ReadStreakResponse> weeklyStreaks,
		List<ReadStreakResponse> monthlyStreaks
	) {
		this.dailyStreaks = dailyStreaks;
		this.weeklyStreaks = weeklyStreaks;
		this.monthlyStreaks = monthlyStreaks;
	}

	public static ListStreakResponse of(
		List<ReadStreakResponse> dailyStreaks,
		List<ReadStreakResponse> weeklyStreaks,
		List<ReadStreakResponse> monthlyStreaks
	) {
		return new ListStreakResponse(dailyStreaks, weeklyStreaks, monthlyStreaks);
	}
}
