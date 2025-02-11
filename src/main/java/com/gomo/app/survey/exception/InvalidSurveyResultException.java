package com.gomo.app.survey.exception;

import com.gomo.app.common.exception.DomainException;

public class InvalidSurveyResultException extends DomainException {

	public InvalidSurveyResultException(SurveyResultErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage());
	}

	public InvalidSurveyResultException(SurveyResultErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage(), cause);
	}
}
