package com.gomo.app.core.member.presentation.response;

import lombok.Getter;

@Getter
public class UpdateProfileImageResponse {

	private String profileImageUrl;

	private UpdateProfileImageResponse(String profileImageUrl) {
		this.profileImageUrl = profileImageUrl;
	}

	public static UpdateProfileImageResponse of(String profileImageUrl) {
		return new UpdateProfileImageResponse(profileImageUrl);
	}
}
