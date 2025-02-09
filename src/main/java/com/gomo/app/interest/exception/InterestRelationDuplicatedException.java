package com.gomo.app.interest.exception;

import com.gomo.app.common.exception.DomainException;

public class InterestRelationDuplicatedException extends DomainException {

	public InterestRelationDuplicatedException(InterestRelationErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage());
	}

	public InterestRelationDuplicatedException(InterestRelationErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage(), cause);
	}
}
