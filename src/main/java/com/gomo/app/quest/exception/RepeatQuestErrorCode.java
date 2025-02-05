package com.gomo.app.quest.exception;

import lombok.Getter;

@Getter
public enum RepeatQuestErrorCode {

	NOT_FOUND("Repeat quest not found with id: ", 404),
	ACCESS_DENIED("Access denied for the repeat quest", 403),
	THRESHOLD_EXCEEDED("Repeat quest threshold exceeded", 422);

	private final String message;
	private final int httpStatus;

	RepeatQuestErrorCode(String message, int httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}
}
