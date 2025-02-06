package com.gomo.app.streak.exception;

import com.gomo.app.common.exception.DomainException;

public class StreakUpdateFailureException extends DomainException {

	public StreakUpdateFailureException(StreakErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage());
	}

	public StreakUpdateFailureException(StreakErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.name(), errorCode.getMessage(), cause);
	}
}
