package com.gomo.app.member.application.port.dto;

import com.gomo.app.member.domain.model.QuestProperty;

public record QuestPropertyDto(int dailyThreshold, int weeklyThreshold, int monthlyThreshold) {

	public static QuestPropertyDto from(QuestProperty questProperty) {
		return new QuestPropertyDto(
			questProperty.getDailyThreshold().getThreshold(),
			questProperty.getWeeklyThreshold().getThreshold(),
			questProperty.getMonthlyThreshold().getThreshold()
		);
	}
}
