package com.gomo.app.core.point.exception;

import com.gomo.app.common.exception.ApplicationException;
import com.gomo.app.core.point.exception.code.PointErrorCode;

public class PointConstraintViolationException extends ApplicationException {

	public PointConstraintViolationException(PointErrorCode errorCode) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage());
	}

	public PointConstraintViolationException(PointErrorCode errorCode, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.getErrorCode(), errorCode.getMessage(), cause);
	}
}
