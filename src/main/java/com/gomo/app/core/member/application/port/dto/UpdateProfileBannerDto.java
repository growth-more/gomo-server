package com.gomo.app.core.member.application.port.dto;

public record UpdateProfileBannerDto(String profileBannerUrl) {

	public static UpdateProfileBannerDto of(String profileBannerUrl) {
		return new UpdateProfileBannerDto(profileBannerUrl);
	}
}
