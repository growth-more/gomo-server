package com.gomo.app.common.exception;

import lombok.Getter;

@Getter
public class ApplicationException extends RuntimeException {

	private int httpStatus;
	private String errorCode;

	public ApplicationException(int httpStatus, String errorCode, String message) {
		super(message);
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
	}

	public ApplicationException(int httpStatus, String errorCode, String message, Throwable cause) {
		super(message, cause);
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
	}
}
