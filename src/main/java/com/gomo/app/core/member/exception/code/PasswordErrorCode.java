package com.gomo.app.core.member.exception.code;

import lombok.Getter;

@Getter
public enum PasswordErrorCode {

	BLANK(422, "MEM-PAS-001", "Password must not be blank"),
	TOO_SHORT(422, "MEM-PAS-002", "Password must be at least 8 characters"),
	TOO_LONG(422, "MEM-PAS-003", "Password must not exceed 64 characters"),
	FORBIDDEN(422, "MEM-PAS-004", "Password must not contain forbidden characters");

	private final int httpStatus;
	private final String errorCode;
	private final String message;

	PasswordErrorCode(int httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}
}
