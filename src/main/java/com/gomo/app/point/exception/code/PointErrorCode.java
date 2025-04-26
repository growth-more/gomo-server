package com.gomo.app.point.exception.code;

import lombok.Getter;

@Getter
public enum PointErrorCode {

	NOT_FOUND(404, "POI-ROO-001", "Point not found"),
	ACCESS_DENIED(403, "POI-ROO-002", "Access denied for the point"),
	NEGATIVE(422, "POI-ROO-003", "Point amount not allowed negative value");

	private final int httpStatus;
	private final String errorCode;
	private final String message;

	PointErrorCode(int httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}
}
