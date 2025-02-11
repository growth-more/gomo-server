package com.gomo.app.survey.exception;

import lombok.Getter;

@Getter
public enum SurveyResultErrorCode {

	MISSING_CUSTOM_INPUT("Custom answer is required when selecting 'Other'", 422),
	UNEXPECTED_CUSTOM_INPUT("Custom answer should not be provided when selecting a predefined option", 422);

	private final String message;
	private final int httpStatus;

	SurveyResultErrorCode(String message, int httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}
}
