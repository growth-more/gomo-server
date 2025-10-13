package com.gomo.app.support.llm.application;

import java.util.Map;

import com.gomo.app.core.quest.domain.model.quest.QuestType;

public record GenerateTextCommand(Map<String, Long> interests, QuestType questType, int amount) {
	public static GenerateTextCommand of(Map<String, Long> interests, QuestType questType, int amount) {
		return new GenerateTextCommand(interests, questType, amount);
	}
}
