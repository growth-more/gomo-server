package com.gomo.app.interest.exception;

import lombok.Getter;

@Getter
public enum InterestErrorCode {

	ACCESS_DENIED("Access denied for the interest", 403),
	PROFICIENCY_ENHANCEMENT_CONFLICT("Failed to update proficiency after multiple attempts", 409),
	TOTAL_SCORE_TOO_LARGE("The given total score exceeds the allowable range", 422),
	INVALID_DELTA_TOTAL_SCORE("Proficiency adjustment failed total score cannot be negative", 422),
	LOGO_IMAGE_TOO_LARGE("Logo image size too large", 413);

	private final String message;
	private final int httpStatus;

	InterestErrorCode(String message, int httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}
}
