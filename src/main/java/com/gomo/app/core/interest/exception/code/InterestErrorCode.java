package com.gomo.app.core.interest.exception.code;

import lombok.Getter;

@Getter
public enum InterestErrorCode {

	NOT_FOUND(404, "INT-ROO-001", "Interest not found"),
	ACCESS_DENIED(403, "INT-ROO-002", "Access denied for the interest"),
	EXCEED_QUOTA(422, "INT-ROO-003", "Exceeded the maximum number of allowed interests");

	private final int httpStatus;
	private final String errorCode;
	private final String message;

	InterestErrorCode(int httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}
}
