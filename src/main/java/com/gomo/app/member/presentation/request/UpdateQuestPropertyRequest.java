package com.gomo.app.member.presentation.request;

import lombok.Getter;

@Getter
public class UpdateQuestPropertyRequest {

	private int dailyThreshold;
	private int weeklyThreshold;
	private int monthlyThreshold;

	private UpdateQuestPropertyRequest(
		int dailyThreshold,
		int weeklyThreshold,
		int monthlyThreshold
	) {
		this.dailyThreshold = dailyThreshold;
		this.weeklyThreshold = weeklyThreshold;
		this.monthlyThreshold = monthlyThreshold;
	}

	public static UpdateQuestPropertyRequest of(
		int dailyThreshold,
		int weeklyThreshold,
		int monthlyThreshold
	) {
		return new UpdateQuestPropertyRequest(dailyThreshold, weeklyThreshold, monthlyThreshold);
	}
}
