package com.gomo.app.core.quest.exception.code;

import lombok.Getter;

@Getter
public enum RepeatQuestErrorCode {

	NOT_FOUND(404, "REP-ROO-001", "Repeat quest not found"),
	ACCESS_DENIED(403, "REP-ROO-002", "Access denied for the repeat quest");

	private final int httpStatus;
	private final String errorCode;
	private final String message;

	RepeatQuestErrorCode(int httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}
}
