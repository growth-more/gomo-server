package com.gomo.app.quest.exception;

import lombok.Getter;

@Getter
public enum AssignQuestErrorCode {

	ACCESS_DENIED("Access denied for the assign quest", 403),
	THRESHOLD_EXCEEDED("Assign quest threshold exceeded", 422);

	private final String message;
	private final int httpStatus;

	AssignQuestErrorCode(String message, int httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}
}
