package com.gomo.app.core.member.exception.code;

import lombok.Getter;

@Getter
public enum MottoErrorCode {

	TOO_LONG(422, "MEM-MOT-001", "Motto must not exceed 200 characters"),
	FORBIDDEN(422, "MEM-MOT-002", "Motto must not contain forbidden characters");

	private final int httpStatus;
	private final String errorCode;
	private final String message;

	MottoErrorCode(int httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}
}
