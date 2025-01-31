package com.gomo.app.interest.exception;

import com.gomo.app.common.exception.DomainException;

public class MajorInterestAccessDeniedException extends DomainException {

	public MajorInterestAccessDeniedException(MajorInterestErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage());
	}

	public MajorInterestAccessDeniedException(MajorInterestErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage(), cause);
	}
}
