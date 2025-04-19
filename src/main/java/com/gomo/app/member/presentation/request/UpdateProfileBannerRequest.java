package com.gomo.app.member.presentation.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;

@Getter
public class UpdateProfileBannerRequest {

	private MultipartFile profileBanner;

	private UpdateProfileBannerRequest() {
	}

	public UpdateProfileBannerRequest(MultipartFile profileBanner) {
		this.profileBanner = profileBanner;
	}

	public static UpdateProfileBannerRequest of(MultipartFile profileBanner) {
		return new UpdateProfileBannerRequest(profileBanner);
	}
}
