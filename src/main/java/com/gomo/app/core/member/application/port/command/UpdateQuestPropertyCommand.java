package com.gomo.app.core.member.application.port.command;

import java.util.UUID;

import com.gomo.app.core.member.domain.model.QuestProperty;

public record UpdateQuestPropertyCommand(UUID memberId, int dailyThreshold, int weeklyThreshold, int monthlyThreshold) {

	public static UpdateQuestPropertyCommand of(UUID memberId, int dailyThreshold, int weeklyThreshold, int monthlyThreshold) {
		return new UpdateQuestPropertyCommand(memberId, dailyThreshold, weeklyThreshold, monthlyThreshold);
	}

	public QuestProperty toDomain() {
		return QuestProperty.of(dailyThreshold, weeklyThreshold, monthlyThreshold);
	}
}
