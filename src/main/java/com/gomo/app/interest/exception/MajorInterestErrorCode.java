package com.gomo.app.interest.exception;

import lombok.Getter;

@Getter
public enum MajorInterestErrorCode {

	NOT_FOUND("Major interest not found", 404),
	ACCESS_DENIED("Access denied for the major interest", 403),
	DUPLICATED("Already registered as a major interest", 409);

	private final String message;
	private final int httpStatus;

	MajorInterestErrorCode(String message, int httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}
}
