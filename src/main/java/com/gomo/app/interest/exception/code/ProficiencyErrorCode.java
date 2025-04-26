package com.gomo.app.interest.exception.code;

import lombok.Getter;

@Getter
public enum ProficiencyErrorCode {

	EXCEED_MAXIMUM_TOTAL_SCORE(422, "INT-PRO-001", "Total score cannot exceed maximum total score"),
	NEGATIVE_TOTAL_SCORE(422, "INT-PRO-002", "Total score cannot be negative");

	private final int httpStatus;
	private final String errorCode;
	private final String message;

	ProficiencyErrorCode(int httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}
}
