package com.gomo.app.core.streak.domain.exception;

import com.gomo.app.common.exception.ApplicationException;

public class AchieverNotFoundException extends ApplicationException {

	public AchieverNotFoundException(AchieverErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());

	}

	public AchieverNotFoundException(AchieverErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
