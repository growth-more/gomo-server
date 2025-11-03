package com.gomo.app.core.point.domain.exception.code;

import lombok.Getter;

@Getter
public enum BalanceErrorCode {

	INSUFFICIENT_BALANCE(422, "WAL-BAL-001", "Adjust fail due to insufficient balance");

	private final int httpStatus;
	private final String errorCode;
	private final String message;

	BalanceErrorCode(int httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}
}
