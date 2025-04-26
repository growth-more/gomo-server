package com.gomo.app.member.exception.code;

import lombok.Getter;

@Getter
public enum MemberNameErrorCode {

	BLANK(422, "MEM-NAM-001", "Member name must not be blank"),
	TOO_SHORT(422, "MEM-NAM-002", "Member name must be at least 2 characters"),
	TOO_LONG(422, "MEM-NAM-003", "Member name must not exceed 20 characters"),
	FORBIDDEN(422, "MEM-NAM-004", "Member name must not contain forbidden characters");

	private final int httpStatus;
	private final String errorCode;
	private final String message;

	MemberNameErrorCode(int httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}
}
