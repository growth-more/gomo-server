package com.gomo.app.support.llm.application.port.dto;

public record LlmRequest(String prompt) {
	public static LlmRequest of(String prompt) {
		return new LlmRequest(prompt);
	}
}
