package com.gomo.app.member.exception.code;

import lombok.Getter;

@Getter
public enum MemberErrorCode {

	NOT_FOUND(404, "MEM-ROO-001", "Member not found"),
	ACCESS_DENIED(403, "MEM-ROO-002", "Access denied for the member"),
	AUTHENTICATION_FAILED(401, "MEM-ROO-003", "Member Authentication fail"),
	UNSUPPORTED_LOGIN_METHOD(401, "MEM-ROO-004", "OAuth member cannot login with password"),;

	private final int httpStatus;
	private final String errorCode;
	private final String message;

	MemberErrorCode(int httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}
}
