package com.gomo.app.core.streak.domain.exception;

import lombok.Getter;

@Getter
public enum AchieverErrorCode {

	NOT_FOUND(404, "ACH-ROO-001", "Achiever not found"),
	ACCESS_DENIED(403, "ACH-ROO-002", "Access denied for the achiever");

	private final int httpStatus;
	private final String errorCode;
	private final String message;

	AchieverErrorCode(int httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}
}
