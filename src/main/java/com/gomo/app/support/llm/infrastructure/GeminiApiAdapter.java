package com.gomo.app.support.llm.infrastructure;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

import com.gomo.app.common.arch.Adapter;
import com.gomo.app.support.llm.application.GenerateTextCommand;
import com.gomo.app.support.llm.application.GenerateTextDto;
import com.gomo.app.support.llm.exception.GenerateQuestException;
import com.gomo.app.support.llm.exception.GenerateQuestErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Adapter
@Slf4j
public class GeminiApiAdapter {
	private final RestClient restClient;

	@Value("${spring.ai.openai.api-key}")
	private String apiKey;

	@Value("${spring.ai.openai.chat.options.model}")
	private String model;

	private static final String GEMINI_API_URL = "https://generativelanguage.googleapis.com/v1beta/openai/chat/completions";

	public GenerateTextDto generate(GenerateTextCommand command){
		try{
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

	private GeminiRequest createGeminiRequest(GenerateTextCommand command){
		return GeminiRequest.createPrompt(command.interests(), command.questType(), command.amount());
	}

	private GenerateTextDto convertToGenerateTextDto(GeminiResponse response){
		if (response.choices() == null || response.choices().isEmpty()){
			throw new GenerateQuestException(GenerateQuestErrorCode.EMPTY_RESPONSE);
		}

		String generatedText = response.choices().get(0).message().content();
		return new GenerateTextDto(generatedText);
	}
}
