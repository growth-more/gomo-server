package com.gomo.app.core.quest.domain.exception.code;

import lombok.Getter;

@Getter
public enum QuestContentErrorCode {

	BLANK(422, "QUE-CON-001", "Quest content must not be blank"),
	TOO_SHORT(422, "QUE-CON-002", "Quest content must be at least 3 characters"),
	TOO_LONG(422, "QUE-CON-003", "Quest content must not exceed 30 characters"),
	FORBIDDEN(422, "QUE-CON-004", "Quest content must not contain forbidden characters");

	private final int httpStatus;
	private final String errorCode;
	private final String message;

	QuestContentErrorCode(int httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}
}
