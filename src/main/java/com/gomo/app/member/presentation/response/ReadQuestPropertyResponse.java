package com.gomo.app.member.presentation.response;

import com.gomo.app.member.domain.model.QuestProperty;
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

	public static ReadQuestPropertyResponse of(QuestProperty questProperty) {
		return new ReadQuestPropertyResponse(questProperty.getDailyThreshold().getThreshold(), questProperty.getWeeklyThreshold().getThreshold(), questProperty.getMonthlyThreshold().getThreshold());
	}
}
