package com.gomo.app.interest.exception.code;

import lombok.Getter;

@Getter
public enum InterestRelationErrorCode {

	NOT_FOUND(404, "INT-REL-001", "Interest relation not found"),
	ACCESS_DENIED(403 ,"INT-REL-002", "Access denied for the interest relation"),
	DUPLICATED(409, "INT-REL-003", "Interest relation already exists"),
	UNEXPECTED_CYCLE(422, "INT-REL-004", "Cycle detected in the interest network by adding this relation, which is not allowed");

	private final int httpStatus;
	private final String errorCode;
	private final String message;

	InterestRelationErrorCode(int httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}
}
