package com.gomo.app.interest.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.interest.exception.code.InterestRelationErrorCode;

public class InterestRelationDuplicatedException extends ApplicationException {

	public InterestRelationDuplicatedException(InterestRelationErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public InterestRelationDuplicatedException(InterestRelationErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
