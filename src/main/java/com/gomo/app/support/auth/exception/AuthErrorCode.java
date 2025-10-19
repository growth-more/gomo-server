package com.gomo.app.support.auth.exception;

import lombok.Getter;

@Getter
public enum AuthErrorCode {

	INVALID_AUTH_CODE(401, "AUT-ROO-001", "Auth code is incorrect"),
	MISSING_REFRESH_TOKEN(401, "AUT-ROO-002", "Refresh token not found"),
	INVALID_REFRESH_TOKEN(401, "AUT-ROO-003", "Refresh token is incorrect"),
	UNSUPPORTED_LOGIN_METHOD(401, "AUT-ROO-004", "OAuth member cannot login with password");

	private final int httpStatus;
	private final String errorCode;
	private final String message;

	AuthErrorCode(int httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}
}
