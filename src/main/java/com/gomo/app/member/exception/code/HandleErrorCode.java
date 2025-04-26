package com.gomo.app.member.exception.code;

import lombok.Getter;

@Getter
public enum HandleErrorCode {

	BLANK(422, "MEM-HAN-001", "Handle must not be blank"),
	TOO_SHORT(422, "MEM-HAN-002", "Handle must be at least 3 characters"),
	TOO_LONG(422, "MEM-HAN-003", "Handle must not exceed 30 characters"),
	FORBIDDEN(422, "MEM-HAN-004", "Handle must not contain forbidden characters"),
	DUPLICATED(409, "MEM-HAN-005", "Handle already exists");

	private final int httpStatus;
	private final String errorCode;
	private final String message;

	HandleErrorCode(int httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}
}
