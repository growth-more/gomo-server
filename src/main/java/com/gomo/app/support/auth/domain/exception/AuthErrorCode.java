package com.gomo.app.support.auth.domain.exception;

import lombok.Getter;

@Getter
public enum AuthErrorCode {

	INVALID_AUTH_CODE(401, "AUT-ROO-001", "Auth code is incorrect"),
	MISSING_REFRESH_TOKEN(401, "AUT-ROO-002", "Refresh token not found"),
	INVALID_REFRESH_TOKEN(401, "AUT-ROO-003", "Refresh token is incorrect"),
	UNSUPPORTED_LOGIN_METHOD(401, "AUT-ROO-004", "OAuth member cannot login with password"),
	PRINCIPAL_DUPLICATED(409, "AUT-ROO-005", "Principal already exists"),
	PRINCIPAL_NOT_FOUND(404, "AUT-ROO-006", "Principal not found"),
	INVALID_VERIFIED_EMAIL_TOKEN(401, "AUT-ROO-007", "Verified email token is incorrect");

	private final int httpStatus;
	private final String errorCode;
	private final String message;

	AuthErrorCode(int httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}
}
