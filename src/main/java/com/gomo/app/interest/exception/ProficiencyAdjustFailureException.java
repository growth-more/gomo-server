package com.gomo.app.interest.exception;

import com.gomo.app.common.exception.DomainException;

public class ProficiencyAdjustFailureException extends DomainException {

	public ProficiencyAdjustFailureException(InterestErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage());
	}

	public ProficiencyAdjustFailureException(InterestErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage(), cause);
	}
}
