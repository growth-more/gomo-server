package com.gomo.app.common.exception;

public class ImageProcessingException extends DomainException {

	public ImageProcessingException(DomainErrorCode errorCode, String message) {
		super(errorCode.getHttpStatus(), errorCode.name(), message);
	}

	public ImageProcessingException(DomainErrorCode errorCode, String message, Throwable cause) {
		super(errorCode.getHttpStatus(), errorCode.name(), message, cause);
	}
}
