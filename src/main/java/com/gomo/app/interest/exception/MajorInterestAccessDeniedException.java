package com.gomo.app.interest.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.interest.exception.code.MajorInterestErrorCode;

public class MajorInterestAccessDeniedException extends ApplicationException {

	public MajorInterestAccessDeniedException(MajorInterestErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public MajorInterestAccessDeniedException(MajorInterestErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
