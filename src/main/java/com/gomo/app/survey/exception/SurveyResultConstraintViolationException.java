package com.gomo.app.survey.exception;

import com.gomo.app.common.exception.ApplicationException;

public class SurveyResultConstraintViolationException extends ApplicationException {

	public SurveyResultConstraintViolationException(SurveyResultErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public SurveyResultConstraintViolationException(SurveyResultErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
