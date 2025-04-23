package com.gomo.app.streak.exception;

import lombok.Getter;

@Getter
public enum StreakErrorCode {

	ACCESS_DENIED("Access denied for the streak", 403);

	private final String message;
	private final int httpStatus;

	StreakErrorCode(String message, int httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}
}
