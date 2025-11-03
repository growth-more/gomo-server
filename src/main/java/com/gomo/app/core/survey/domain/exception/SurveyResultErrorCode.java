package com.gomo.app.core.survey.domain.exception;

import lombok.Getter;

@Getter
public enum SurveyResultErrorCode {

	TOO_LONG(422, "SUR-RES-001", "Custom answer must not exceed 20 characters"),
	FORBIDDEN(422, "SUR-RES-002", "Custom answer cannot contain forbidden characters"),
	MISSING_CUSTOM_ANSWER(422, "SUR-RES-003", "Custom answer must not be blank when selecting 'Other'"),
	UNEXPECTED_CUSTOM_ANSWER(422, "SUR-RES-004", "Custom answer should not be provided when selecting a predefined option");

	private final int httpStatus;
	private final String errorCode;
	private final String message;

	SurveyResultErrorCode(int httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}
}
