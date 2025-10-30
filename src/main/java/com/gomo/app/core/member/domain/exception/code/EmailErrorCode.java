package com.gomo.app.core.member.domain.exception.code;

import lombok.Getter;

@Getter
public enum EmailErrorCode {

	BLANK(422, "MEM-EMA-001", "Email must not be blank"),
	TOO_SHORT(422, "MEM-EMA-002", "Email must be at least 10 characters"),
	TOO_LONG(422, "MEM-EMA-003", "Email must not exceed 254 characters"),
	INVALID_FORMAT(422, "MEM-EMA-004", "Email must be valid format"),
	DUPLICATED(409, "MEM-EMA-005", "Email already exists");

	private final int httpStatus;
	private final String errorCode;
	private final String message;

	EmailErrorCode(int httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}
}
