package com.gomo.app.core.interest.domain.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.core.interest.domain.exception.code.LevelErrorCode;

public class LevelConstraintViolationException extends ApplicationException {

	public LevelConstraintViolationException(LevelErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public LevelConstraintViolationException(LevelErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
