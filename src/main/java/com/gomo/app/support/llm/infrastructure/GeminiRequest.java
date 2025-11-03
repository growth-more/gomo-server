package com.gomo.app.support.llm.infrastructure;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.gomo.app.support.llm.util.PromptLoader;
import com.gomo.app.core.quest.domain.model.quest.QuestType;

public record GeminiRequest(
	String model,
	String reasoning_effort,
	List<Prompt> messages
) {
	private static Prompt createSystemPrompt(PromptLoader promptLoader){
		String content = promptLoader.loadPrompt("quest/system-prompt.txt");
		return new Prompt("system", content);
	}

	private static Prompt createUserPrompt(Map<String, Long> interests, QuestType questType, int amount){
		String interestsText = interests.entrySet().stream()
			.map(entry -> entry.getKey() + "(숙련도 : " + entry.getValue() + ")")
			.collect(Collectors.joining(", "));

		String promptText = String.format(
			"**개인 정보**\n- 관심사: %s \n- 퀘스트 타입: %s \n- 퀘스트 수: %s", interestsText, questType.name(), amount
		);

		return new Prompt("user", promptText);
	}

	public static GeminiRequest createPrompt(Map<String, Long> interests, QuestType questType, int amount, PromptLoader promptLoader){
		Prompt system = createSystemPrompt(promptLoader);
		Prompt user = createUserPrompt(interests, questType, amount);
		return new GeminiRequest("gemini-2.5-flash", "none", List.of(system, user));
	}

	public record Prompt(String role, String content){}
}