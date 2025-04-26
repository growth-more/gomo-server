package com.gomo.app.interest.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.interest.exception.code.InterestErrorCode;

public class InterestNotFoundException extends ApplicationException {

	public InterestNotFoundException(InterestErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public InterestNotFoundException(InterestErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
