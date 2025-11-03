package com.gomo.app.support.llm.infrastructure;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gomo.app.common.arch.Adapter;
import com.gomo.app.support.llm.application.GenerateTextCommand;
import com.gomo.app.support.llm.application.GenerateTextDto;
import com.gomo.app.support.llm.application.LlmClientPortOut;
import com.gomo.app.support.llm.exception.LlmErrorCode;
import com.gomo.app.support.llm.exception.LlmException;
import com.gomo.app.support.llm.util.PromptLoader;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Adapter
class GeminiApiAdapter implements LlmClientPortOut {
	private final RestClient restClient;

	@Value("${spring.ai.openai.api-key}")
	private String apiKey;

	@Value("${spring.ai.openai.chat.options.model}")
	private String model;

	private PromptLoader promptLoader;

	private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/openai/chat/completions";

	public GenerateTextDto generate(GenerateTextCommand command) {
		try {
			String apiKey = "Bearer " + this.apiKey;
			GeminiRequest request = createGeminiRequest(command);

			GeminiResponse response = restClient.post()
				.uri(GEMINI_API_URL)
				.header("Authorization", apiKey)
				.contentType(MediaType.APPLICATION_JSON)
				.body(request)
				.retrieve()
				.body(GeminiResponse.class);

			return convertToGenerateTextDto(response);
		} catch (Exception e) {
			log.error("Failed to generate text with Gemini API", e);
			throw new RuntimeException("Gemini API 호출 중 오류가 발생 했습니다.", e);
		}
	}

	private GeminiRequest createGeminiRequest(GenerateTextCommand command) {
		return GeminiRequest.createPrompt(command.interests(), command.questType(), command.amount(), promptLoader);
	}

	private GenerateTextDto convertToGenerateTextDto(GeminiResponse response) {
		if (response.choices() == null || response.choices().isEmpty()) {
			throw new LlmException(LlmErrorCode.EMPTY_RESPONSE);
		}

		String generatedText = response.choices().get(0).message().content();
		return new GenerateTextDto(parseDtofromText(generatedText));
	}

	private Map<String, List<String>> parseDtofromText(String text) {
		try {
			String cleanText = text.trim();

			if (cleanText.startsWith("```json")) {
				cleanText = cleanText.substring(7);
			}

			if (cleanText.endsWith("```")) {
				cleanText = cleanText.substring(0, cleanText.length() - 3);
			}

			cleanText = cleanText.trim();
			ObjectMapper objectMapper = new ObjectMapper();
			TypeReference<Map<String, List<String>>> typeRef = new TypeReference<Map<String, List<String>>>() {
			};

			return objectMapper.readValue(cleanText, typeRef);
		} catch (JsonProcessingException e) {
			throw new LlmException(LlmErrorCode.INVALID_JSON_FORMAT);
		} catch (Exception e) {
			throw new LlmException(LlmErrorCode.PARSING_ERROR);
		}
	}
}
