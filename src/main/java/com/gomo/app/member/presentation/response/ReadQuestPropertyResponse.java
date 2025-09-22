package com.gomo.app.member.presentation.response;

import com.gomo.app.member.application.port.dto.QuestPropertyDto;

import lombok.Getter;

@Getter
public class ReadQuestPropertyResponse {

	private final int dailyThreshold;
	private final int weeklyThreshold;
	private final int monthlyThreshold;

	private ReadQuestPropertyResponse(
		int dailyThreshold,
		int weeklyThreshold,
		int monthlyThreshold
	) {
		this.dailyThreshold = dailyThreshold;
		this.weeklyThreshold = weeklyThreshold;
		this.monthlyThreshold = monthlyThreshold;
	}

	public static ReadQuestPropertyResponse of(QuestPropertyDto dto) {
		return new ReadQuestPropertyResponse(dto.dailyThreshold(), dto.weeklyThreshold(), dto.monthlyThreshold());
	}
}
