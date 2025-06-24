package com.gomo.app.quest.exception.code;

import lombok.Getter;

@Getter
public enum AssignQuestErrorCode {

	NOT_FOUND(404, "ASS-ROO-001", "Assign quest not found"),
	ACCESS_DENIED(403, "ASS-ROO-002", "Access denied for the assign quest"),
	NOT_CONFIRMED(422, "ASS-ROO-003", "Assign quest has not been confirmed"),
	ALREADY_CONFIRMED(422, "ASS-ROO-004", "Assign quest has already been confirmed"),
	ALREADY_COMPLETED(422, "ASS-ROO-005", "Assign quest has already been completed"),
	NOT_ALLOWED_ORDER_CHANGE(422, "ASS-ROO-006", "Completed quests cannot have their order changed"),
	INVALID_PERIOD_TYPE(422, "ASS-ROO-007", "The requested period type is invalid");

	private final int httpStatus;
	private final String errorCode;
	private final String message;

	AssignQuestErrorCode(int httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}
}
