package com.gomo.app.member.presentation.response;

import lombok.Getter;

@Getter
public class ReadQuestPropertyResponse {

	private int dailyThreshold;
	private int weeklyThreshold;
	private int monthlyThreshold;

	private ReadQuestPropertyResponse(
		int dailyThreshold,
		int weeklyThreshold,
		int monthlyThreshold
	) {
		this.dailyThreshold = dailyThreshold;
		this.weeklyThreshold = weeklyThreshold;
		this.monthlyThreshold = monthlyThreshold;
	}

	public static ReadQuestPropertyResponse of(
		int dailyThreshold,
		int weeklyThreshold,
		int monthlyThreshold
	) {
		return new ReadQuestPropertyResponse(dailyThreshold, weeklyThreshold, monthlyThreshold);
	}
}
