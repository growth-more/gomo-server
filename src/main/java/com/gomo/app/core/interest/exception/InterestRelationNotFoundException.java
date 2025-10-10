package com.gomo.app.core.interest.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.core.interest.exception.code.InterestRelationErrorCode;

public class InterestRelationNotFoundException extends ApplicationException {

	public InterestRelationNotFoundException(InterestRelationErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public InterestRelationNotFoundException(InterestRelationErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
