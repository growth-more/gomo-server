package com.gomo.app.support.image.application.port.dto;

public record UploadImageDto(String url) {

	public static UploadImageDto of(String url) {
		return new UploadImageDto(url);
	}
}
