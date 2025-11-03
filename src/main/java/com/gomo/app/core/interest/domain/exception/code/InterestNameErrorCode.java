package com.gomo.app.core.interest.domain.exception.code;

import lombok.Getter;

@Getter
public enum InterestNameErrorCode {

	BLANK(422, "INT-NAM-001", "Interest name must not be blank"),
	TOO_LONG(422, "INT-NAM-002", "Interest name must not exceed 25 characters"),
	FORBIDDEN(422, "INT-NAM-003", "Interest name must not contain forbidden characters");

	private final int httpStatus;
	private final String errorCode;
	private final String message;

	InterestNameErrorCode(int httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}
}
