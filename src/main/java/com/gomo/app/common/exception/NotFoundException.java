package com.gomo.app.common.exception;

public class NotFoundException extends DomainException {

	public NotFoundException(DomainErrorCode errorCode, String message) {
		super(errorCode.getHttpStatus(), errorCode.name(), message);
	}

	public NotFoundException(DomainErrorCode errorCode, String message, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.name(), message, cause);
	}
}
