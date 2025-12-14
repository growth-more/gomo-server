package com.gomo.app.support.llm.infrastructure;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import com.gomo.app.common.arch.Adapter;
import com.gomo.app.support.llm.application.port.dto.LlmRequest;
import com.gomo.app.support.llm.application.port.dto.LlmResponse;
import com.gomo.app.support.llm.application.port.out.LlmClientPortOut;
import com.gomo.app.support.llm.exception.LlmErrorCode;
import com.gomo.app.support.llm.exception.LlmException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Adapter
@Slf4j
public class GeminiApiAdapter implements LlmClientPortOut {
	private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/openai/chat/completions";

	private final RestClient restClient;

	@Value("${spring.ai.openai.api-key}")
	private String apiKey;

	@Value("${spring.ai.openai.chat.options.model}")
	private String model;

	@Override
	public LlmResponse generateText(LlmRequest request) {
		try {
			ApiRequest apiRequest = new ApiRequest(
				model,
				"none",
				List.of(new ApiRequest.Message("user", request.prompt()))
			);

			ApiResponse response = restClient.post()
				.uri(GEMINI_API_URL)
				.header("Authorization", "Bearer " + apiKey)
				.contentType(MediaType.APPLICATION_JSON)
				.body(apiRequest)
				.retrieve()
				.body(ApiResponse.class);

			validateResponse(response);
			String generatedText = extractGeneratedText(response);

			return LlmResponse.of(generatedText);
		} catch (LlmException e) {
			// todo to <nurdykim>: custom exception 추가 필요
			throw e;
		} catch (Exception e) {
			// todo to <nurdykim>: custom exception 추가 필요2
			log.error("Failed to generate text with Gemini API", e);
			throw e;
		}
	}

	private void validateResponse(ApiResponse response) {
		if (response == null || response.choices() == null || response.choices().isEmpty()) {
			throw new LlmException(LlmErrorCode.EMPTY_RESPONSE);
		}
	}

	private String extractGeneratedText(ApiResponse response) {
		return response.choices().getFirst().message().content();
	}

	private record ApiRequest(String model, String reasoning_effort, List<Message> messages) {
		record Message(String role, String content) {
		}
	}

	private record ApiResponse(
		List<Choice> choices,
		int created,
		String id,
		String model,
		String object,
		Usage usage
	) {
		record Choice(String finish_reason, int index, Message message) {
		}

		record Message(String content, String role) {
		}

		record Usage(int completion_tokens, int prompt_tokens, int total_tokens) {
		}
	}
}
