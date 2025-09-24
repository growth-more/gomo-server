package com.gomo.app.core.interest.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.core.interest.exception.code.InterestErrorCode;

public class InterestConstraintViolationException extends ApplicationException {

	public InterestConstraintViolationException(InterestErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public InterestConstraintViolationException(InterestErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
