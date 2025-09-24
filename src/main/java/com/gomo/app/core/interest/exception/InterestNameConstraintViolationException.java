package com.gomo.app.core.interest.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.core.interest.exception.code.InterestNameErrorCode;

public class InterestNameConstraintViolationException extends ApplicationException {

	public InterestNameConstraintViolationException(InterestNameErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public InterestNameConstraintViolationException(InterestNameErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
