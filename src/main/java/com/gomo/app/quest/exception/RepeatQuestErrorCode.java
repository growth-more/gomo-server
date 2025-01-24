package com.gomo.app.quest.exception;

import lombok.Getter;

@Getter
public enum RepeatQuestErrorCode {

	NOT_FOUND("Repeat quest not found with id: ", 404),
	INVALID_PARAMETER("Invalid parameter: ", 422);

	private final String message;
	private final int httpStatus;

	RepeatQuestErrorCode(String message, int httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}
}
