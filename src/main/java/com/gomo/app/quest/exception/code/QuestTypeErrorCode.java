package com.gomo.app.quest.exception.code;

import lombok.Getter;

@Getter
public enum QuestTypeErrorCode {

	UNEXPECTED(422, "QUE-TYP-001", "Unexpected quest type"),
	MISMATCHED(422, "QUE-TYP-002", "Assigned quest type does not match the requested quest type");

	private final int httpStatus;
	private final String errorCode;
	private final String message;

	QuestTypeErrorCode(int httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}
}
