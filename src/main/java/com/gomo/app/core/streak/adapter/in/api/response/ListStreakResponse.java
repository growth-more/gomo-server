package com.gomo.app.core.streak.adapter.in.api.response;

import java.util.List;

import com.gomo.app.core.streak.application.port.dto.ListStreakDto;

import lombok.Getter;

@Getter
public class ListStreakResponse {

	private final List<ReadStreakResponse> dailyStreaks;
	private final List<ReadStreakResponse> weeklyStreaks;
	private final List<ReadStreakResponse> monthlyStreaks;

	private ListStreakResponse(List<ReadStreakResponse> dailyStreaks, List<ReadStreakResponse> weeklyStreaks, List<ReadStreakResponse> monthlyStreaks) {
		this.dailyStreaks = dailyStreaks;
		this.weeklyStreaks = weeklyStreaks;
		this.monthlyStreaks = monthlyStreaks;
	}

	public static ListStreakResponse from(ListStreakDto dto) {
		return new ListStreakResponse(
			dto.dailyStreaks().stream().map(ReadStreakResponse::from).toList(),
			dto.weeklyStreaks().stream().map(ReadStreakResponse::from).toList(),
			dto.monthlyStreaks().stream().map(ReadStreakResponse::from).toList()
		);
	}
}
