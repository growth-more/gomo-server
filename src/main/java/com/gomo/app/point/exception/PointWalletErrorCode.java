package com.gomo.app.point.exception;

import lombok.Getter;

@Getter
public enum PointWalletErrorCode {

	INSUFFICIENT_BALANCE("Adjust fail due to insufficient balance", 422);

	private final String message;
	private final int httpStatus;

	PointWalletErrorCode(String message, int httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}
}
