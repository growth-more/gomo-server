package com.gomo.app.interest.exception;

import com.gomo.app.common.exception.DomainException;

public class InterestQuotaExceededException extends DomainException {

	public InterestQuotaExceededException(InterestErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage());
	}

	public InterestQuotaExceededException(InterestErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage(), cause);
	}
}
