package com.gomo.app.core.member.adapter.in.api.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;

@Getter
public class UpdateProfileBannerRequest {

	private final MultipartFile profileBanner;

	public UpdateProfileBannerRequest(MultipartFile profileBanner) {
		this.profileBanner = profileBanner;
	}

	public static UpdateProfileBannerRequest of(MultipartFile profileBanner) {
		return new UpdateProfileBannerRequest(profileBanner);
	}
}
