package com.gomo.app.quest.exception.code;

import lombok.Getter;

@Getter
public enum QuestErrorCode {

	EXCEED_QUOTA(422, "QUE-ROO-001", "Quest quota exceeded"),
	ACCESS_DENIED(403, "QUE-ROO-002", "Access denied for the quest");

	private final int httpStatus;
	private final String errorCode;
	private final String message;

	QuestErrorCode(int httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}
}
