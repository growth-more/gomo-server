package com.gomo.app.interest.exception;

import com.gomo.app.common.exception.DomainException;

public class InterestRelationAccessDeniedException extends DomainException {

	public InterestRelationAccessDeniedException(InterestRelationErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage());
	}

	public InterestRelationAccessDeniedException(InterestRelationErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage(), cause);
	}
}
