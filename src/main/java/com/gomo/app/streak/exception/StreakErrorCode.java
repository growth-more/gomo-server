package com.gomo.app.streak.exception;

import lombok.Getter;

@Getter
public enum StreakErrorCode {

	UPDATE_CONFLICT("Failed to update Streak after multiple attempts", 409);

	private final String message;
	private final int httpStatus;

	StreakErrorCode(String message, int httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}
}
