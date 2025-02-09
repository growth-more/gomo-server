package com.gomo.app.interest.exception;

import com.gomo.app.common.exception.DomainException;

public class InterestRelationCycleException extends DomainException {

	public InterestRelationCycleException(InterestRelationErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage());
	}

	public InterestRelationCycleException(InterestRelationErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage(), cause);
	}
}
