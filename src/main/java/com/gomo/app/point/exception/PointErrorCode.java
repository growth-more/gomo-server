package com.gomo.app.point.exception;

import lombok.Getter;

@Getter
public enum PointErrorCode {

	NEGATIVE_NOT_ALLOWED("Point amount not allowed negative value", 422);

	private final String message;
	private final int httpStatus;

	PointErrorCode(String message, int httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}
}
