package com.gomo.app.interest.exception;

import lombok.Getter;

@Getter
public enum InterestRelationErrorCode {

	NOT_FOUND("Interest relation not found", 404),
	ACCESS_DENIED("Access denied for the interest relation", 403);

	private final String message;
	private final int httpStatus;

	InterestRelationErrorCode(String message, int httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}
}
