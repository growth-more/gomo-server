package com.gomo.app.support.llm.application.usecase;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.support.llm.application.port.dto.GenerateTextDto;
import com.gomo.app.support.llm.application.port.dto.LlmRequest;
import com.gomo.app.support.llm.application.port.dto.LlmResponse;
import com.gomo.app.support.llm.application.port.in.GenerateQuestTextCommand;
import com.gomo.app.support.llm.application.port.in.GenerateQuestTextPortIn;
import com.gomo.app.support.llm.application.port.out.LlmClientPortOut;
import com.gomo.app.support.llm.exception.LlmErrorCode;
import com.gomo.app.support.llm.exception.LlmException;
import com.gomo.app.support.llm.util.PromptLoader;

import lombok.RequiredArgsConstructor;

@ApplicationService
@RequiredArgsConstructor
class GeminiGenerateTextUseCase implements GenerateQuestTextPortIn {

	private final LlmClientPortOut llmClient;
	private final PromptLoader promptLoader;
	private final ObjectMapper objectMapper;

	@Override
	public GenerateTextDto generate(GenerateQuestTextCommand command) {
		String prompt = buildQuestPrompt(command);
		LlmResponse response = llmClient.generateText(LlmRequest.of(prompt));
		Map<String, List<String>> questsByType = parseQuestJson(response.generatedText());

		return GenerateTextDto.of(questsByType);
	}

	private String buildQuestPrompt(GenerateQuestTextCommand command) {
		String systemPrompt = promptLoader.loadPrompt("quest/system-prompt.txt");
		String userPrompt = buildUserPrompt(command);

		return systemPrompt + "\n\n" + userPrompt;
	}

	private String buildUserPrompt(GenerateQuestTextCommand command) {
		String interestsText = command.interests().entrySet().stream()
			.map(entry -> entry.getKey() + "(숙련도: " + entry.getValue() + ")")
			.collect(Collectors.joining(", "));
		return String.format(
			"**개인 정보**\n- 관심사: %s\n- 퀘스트타입: %s\n-퀘스트 수: %d",
			interestsText,
			command.questType().name(),
			command.amount()
		);
	}

	private Map<String, List<String>> parseQuestJson(String rawText) {
		try {
			String cleanedJson = cleanMarkdownCodeBlock(rawText);
			TypeReference<Map<String, List<String>>> typeRef = new TypeReference<Map<String, List<String>>>() {
			};
			return objectMapper.readValue(cleanedJson, typeRef);
		} catch (JsonProcessingException e) {
			throw new LlmException(LlmErrorCode.INVALID_JSON_FORMAT, e);
		} catch (Exception e) {
			throw new LlmException(LlmErrorCode.PARSING_ERROR, e);
		}
	}

	private String cleanMarkdownCodeBlock(String rawText) {
		String cleaned = rawText.trim();

		if (cleaned.startsWith("```json")) {
			cleaned = cleaned.substring(7);
		} else if (cleaned.startsWith("```")) {
			cleaned = cleaned.substring(3);
		}

		if (cleaned.endsWith("```")) {
			cleaned = cleaned.substring(0, cleaned.length() - 3);
		}

		return cleaned.trim();
	}
}
