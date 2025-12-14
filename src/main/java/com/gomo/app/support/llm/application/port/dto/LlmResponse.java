package com.gomo.app.support.llm.application.port.dto;

public record LlmResponse(String generatedText) {
	public static LlmResponse of(String generatedText) {
		return new LlmResponse(generatedText);
	}
}
