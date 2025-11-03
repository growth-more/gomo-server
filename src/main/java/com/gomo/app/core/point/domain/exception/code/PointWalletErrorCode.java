package com.gomo.app.core.point.domain.exception.code;

import lombok.Getter;

@Getter
public enum PointWalletErrorCode {

	NOT_FOUND(404, "WAL-ROO-001", "Point wallet not found"),
	ACCESS_DENIED(403, "WAL-ROO-002", "Access denied for the point wallet ");

	private final int httpStatus;
	private final String errorCode;
	private final String message;

	PointWalletErrorCode(int httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}
}
