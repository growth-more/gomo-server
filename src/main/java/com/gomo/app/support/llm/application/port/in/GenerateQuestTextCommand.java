package com.gomo.app.support.llm.application.port.in;

import java.util.Map;

import com.gomo.app.core.quest.domain.model.quest.QuestType;

public record GenerateQuestTextCommand(
	Map<String, Long> interests,
	QuestType questType,
	int amount
) {
	public static GenerateQuestTextCommand of(
		Map<String, Long> interests,
		QuestType questType,
		int amount
	) {
		return new GenerateQuestTextCommand(interests, questType, amount);
	}
}
