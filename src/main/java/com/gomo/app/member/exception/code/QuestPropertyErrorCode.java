package com.gomo.app.member.exception.code;

import lombok.Getter;

@Getter
public enum QuestPropertyErrorCode {

	TOO_SMALL(422, "MEM-QUE-001", "Threshold must meet the minimum value"),
	TOO_LARGE(422, "MEM-QUE-002", "Threshold must meet the maximum value"),
	UNEXPECTED_QUEST_TYPE(422, "MEM-QUE-002", "Unexpected quest type");

	private final int httpStatus;
	private final String errorCode;
	private final String message;

	QuestPropertyErrorCode(int httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}
}
