package com.gomo.app.support.llm.exception;

import lombok.Getter;

@Getter
public enum GenerateQuestErrorCode {
	GEMINI_API_ERROR(500, "QUE-GEN-001", "An error occurred while call GeminiAPI"),
	EMPTY_RESPONSE(500, "QUE-GEN-002", "Gemini API response blank"),
	INVALID_RESPONSE_FORMAT(500, "QUE-GEN-003", "Gemini Response format is invalid"),
	INVALID_JSON_FORMAT(500, "QUE-GEN-004", "Gemini Response JSON format is invalid"),
	PARSING_ERROR(500, "QUE-GEN-005", "An error occurred while parse data String to Map");

	private final int httpStatus;
	private final String errorCode;
	private final String message;

	GenerateQuestErrorCode(int httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}
}
