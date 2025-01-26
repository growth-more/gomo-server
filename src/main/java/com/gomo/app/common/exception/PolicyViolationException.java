package com.gomo.app.common.exception;

public class PolicyViolationException extends DomainException {

	public PolicyViolationException(DomainErrorCode errorCode, String message) {
		super(errorCode.getHttpStatus(), errorCode.name(), message);
	}

	public PolicyViolationException(DomainErrorCode errorCode, String message, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.name(), message, cause);
	}
}
