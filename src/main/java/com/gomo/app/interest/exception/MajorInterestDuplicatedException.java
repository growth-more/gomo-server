package com.gomo.app.interest.exception;

import com.gomo.app.common.exception.DomainException;

public class MajorInterestDuplicatedException extends DomainException {

	public MajorInterestDuplicatedException(MajorInterestErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage());
	}

	public MajorInterestDuplicatedException(MajorInterestErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage(), cause);
	}
}
