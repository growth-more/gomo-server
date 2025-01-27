package com.gomo.app.interest.exception;

import lombok.Getter;

@Getter
public enum InterestErrorCode {

	NOT_FOUND("Interest not found", 404),
	ACCESS_DENIED("Access denied for the interest", 403),
	LOGO_IMAGE_TOO_LARGE("Logo image size too large", 413);

	private final String message;
	private final int httpStatus;

	InterestErrorCode(String message, int httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}
}
