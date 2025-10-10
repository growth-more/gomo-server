package com.gomo.app.common.displayorder;

import lombok.Getter;

@Getter
public enum DisplayOrderErrorCode {

	NON_POSITIVE(422, "DIS-ROO-001", "Order display must be positive"),
	NON_POSITIVE_INCREMENT(422, "DIS-ROO-002", "Order display increment must be positive");

	private final int httpStatus;
	private final String errorCode;
	private final String message;

	DisplayOrderErrorCode(int httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}
}
