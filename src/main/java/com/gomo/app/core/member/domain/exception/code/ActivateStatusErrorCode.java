package com.gomo.app.core.member.domain.exception.code;

import lombok.Getter;

@Getter
public enum ActivateStatusErrorCode {

	BLOCKED(403, "MEM-ACT-001", "Member activation status is blocked"),
	DELETED(410, "MEM-ACT-002", "Member activation status is deleted");

	private final int httpStatus;
	private final String errorCode;
	private final String message;

	ActivateStatusErrorCode(int httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}
}
