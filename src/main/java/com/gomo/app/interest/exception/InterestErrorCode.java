package com.gomo.app.interest.exception;

import lombok.Getter;

@Getter
public enum InterestErrorCode {

	NOT_FOUND("Interest not found with id: ", 404),
	INVALID_PARAMETER("Invalid parameter: ", 422),
	LOGO_IMAGE_TOO_LARGE("Logo image size too large", 413);

	private final String message;
	private final int httpStatus;

	InterestErrorCode(String message, int httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}
}
