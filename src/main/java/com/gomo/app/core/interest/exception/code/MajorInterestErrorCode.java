package com.gomo.app.core.interest.exception.code;

import lombok.Getter;

@Getter
public enum MajorInterestErrorCode {

	NOT_FOUND(404, "MAJ-ROO-001", "Major interest not found"),
	ACCESS_DENIED(403, "MAJ-ROO-002", "Access denied for the major interest"),
	DUPLICATED(409, "MAJ-ROO-003", "Already registered as a major interest");

	private final int httpStatus;
	private final String errorCode;
	private final String message;

	MajorInterestErrorCode(int httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}
}
