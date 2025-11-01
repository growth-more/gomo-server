package com.gomo.app.core.quest.domain.exception.code;

import lombok.Getter;

@Getter
public enum QuestPoolErrorCode {

	NOT_FOUND(404, "POO-ROO-001", "Quest pool not found");

	private final int httpStatus;
	private final String errorCode;
	private final String message;

	QuestPoolErrorCode(int httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}
}
