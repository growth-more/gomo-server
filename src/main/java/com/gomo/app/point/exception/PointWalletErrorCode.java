package com.gomo.app.point.exception;

import lombok.Getter;

@Getter
public enum PointWalletErrorCode {

	UPDATE_CONFLICT("Failed to update point after multiple attempts", 409),
	INSUFFICIENT_BALANCE("Adjust fail due to insufficient balance", 422);

	private final String message;
	private final int httpStatus;

	PointWalletErrorCode(String message, int httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}
}
