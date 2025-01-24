package com.gomo.app.common.exception;

import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {

	private int httpStatus;
	private String code;

	public DomainException(int httpStatus, String code, String message) {
		super(message);
		this.httpStatus = httpStatus;
		this.code = code;
	}

	public DomainException(int httpStatus, String code, String message, Throwable cause) {
		super(message, cause);
		this.httpStatus = httpStatus;
		this.code = code;
	}
}
