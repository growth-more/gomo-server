package com.gomo.app.member.presentation.response;

import lombok.Getter;

@Getter
public class UpdateProfileBannerResponse {

	private String profileBannerUrl;

	private UpdateProfileBannerResponse(String profileBannerUrl) {
		this.profileBannerUrl = profileBannerUrl;
	}

	public static UpdateProfileBannerResponse of(String profileBannerUrl) {
		return new UpdateProfileBannerResponse(profileBannerUrl);
	}
}
