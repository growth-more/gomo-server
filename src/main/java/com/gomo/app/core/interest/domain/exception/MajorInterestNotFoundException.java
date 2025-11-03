package com.gomo.app.core.interest.domain.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.core.interest.domain.exception.code.MajorInterestErrorCode;

public class MajorInterestNotFoundException extends ApplicationException {

	public MajorInterestNotFoundException(MajorInterestErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public MajorInterestNotFoundException(MajorInterestErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
