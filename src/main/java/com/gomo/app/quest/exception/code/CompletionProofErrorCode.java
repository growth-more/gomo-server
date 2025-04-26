package com.gomo.app.quest.exception.code;

import lombok.Getter;

@Getter
public enum CompletionProofErrorCode {

	BLANK(422, "QUE-COM-001", "Completion proof must not be blank"),
	TOO_LONG(422, "QUE-COM-002", "Completion proof must not exceed 512 characters");

	private final int httpStatus;
	private final String errorCode;
	private final String message;

	CompletionProofErrorCode(int httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}
}
