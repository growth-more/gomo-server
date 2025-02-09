package com.gomo.app.interest.exception;

import lombok.Getter;

@Getter
public enum InterestRelationErrorCode {

	DUPLICATED("Interest relation already exists", 409),
	CYCLE_OCCUR("Cycle detected in the interest network by adding this relation, which is not allowed", 422),
	ACCESS_DENIED("Access denied for the interest relation", 403);

	private final String message;
	private final int httpStatus;

	InterestRelationErrorCode(String message, int httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}
}
