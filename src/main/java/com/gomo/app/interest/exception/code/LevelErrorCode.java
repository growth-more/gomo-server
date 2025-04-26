package com.gomo.app.interest.exception.code;

import lombok.Getter;

@Getter
public enum LevelErrorCode {

	NEGATIVE(422, "INT-LEV-001", "Level must be positive or zero"),
	EXCEED_MAXIMUM(422, "INT-LEV-002", "Level cannot exceed the maximum level");

	private final int httpStatus;
	private final String errorCode;
	private final String message;

	LevelErrorCode(int httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}
}
