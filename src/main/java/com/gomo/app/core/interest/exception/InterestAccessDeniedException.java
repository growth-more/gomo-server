package com.gomo.app.core.interest.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.core.interest.exception.code.InterestErrorCode;

public class InterestAccessDeniedException extends ApplicationException {

	public InterestAccessDeniedException(InterestErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public InterestAccessDeniedException(InterestErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
