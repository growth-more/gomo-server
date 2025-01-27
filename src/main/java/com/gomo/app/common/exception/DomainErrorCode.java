package com.gomo.app.common.exception;

import lombok.Getter;

@Getter
public enum DomainErrorCode {

	NOT_FOUND(404),
	INVALID_PARAMETER(422),
	IMAGE_PROCESSING_ERROR(500),
	IMAGE_TOO_LARGE(422);

	private final int httpStatus;

	DomainErrorCode(int httpStatus) {
		this.httpStatus = httpStatus;
	}
}
