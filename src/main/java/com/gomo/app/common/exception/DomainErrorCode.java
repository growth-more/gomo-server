package com.gomo.app.common.exception;

import lombok.Getter;

@Getter
public enum DomainErrorCode {

	NOT_FOUND(404),
	INVALID_PARAMETER(422);

	private final int httpStatus;

	DomainErrorCode(int httpStatus) {
		this.httpStatus = httpStatus;
	}
}
