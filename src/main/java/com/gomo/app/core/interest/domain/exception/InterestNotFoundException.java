package com.gomo.app.core.interest.domain.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.core.interest.domain.exception.code.InterestErrorCode;

public class InterestNotFoundException extends ApplicationException {

	public InterestNotFoundException(InterestErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public InterestNotFoundException(InterestErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
