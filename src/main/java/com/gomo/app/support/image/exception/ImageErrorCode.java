package com.gomo.app.support.image.exception;

import lombok.Getter;

@Getter
public enum ImageErrorCode {

	UPLOAD_FAIL(500, "IMA-ROO-002", "An error occurred while uploading the image"),
	READ_FAIL(500, "IMA-ROO-003", "An error occurred while reading the images"),
	DELETE_FAIL(500, "IMA-ROO-004", "An error occurred while deleting the image");

	private final int httpStatus;
	private final String errorCode;
	private final String message;

	ImageErrorCode(int httpStatus, String errorCode, String message) {
		this.httpStatus = httpStatus;
		this.errorCode = errorCode;
		this.message = message;
	}
}
