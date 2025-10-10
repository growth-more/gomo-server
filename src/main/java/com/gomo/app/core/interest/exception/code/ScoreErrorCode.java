package com.gomo.app.core.interest.exception.code;

import lombok.Getter;

@Getter
public enum ScoreErrorCode {

	NON_POSITIVE_INCREMENT(422, "INT-SCO-001", "Score increment must be positive");

	private final int httpStatus;
	private final String errorCode;
	private final String message;

	ScoreErrorCode(int httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}
}
