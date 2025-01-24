package com.gomo.app.member.presentation.response;

import lombok.Getter;

@Getter
public class UpdateProfileImageResponse {

	private String profileImageUrl;
	private String profileImageName;

	private UpdateProfileImageResponse(
		String profileImageUrl,
		String profileImageName
	) {
		this.profileImageUrl = profileImageUrl;
		this.profileImageName = profileImageName;
	}

	public static UpdateProfileImageResponse of(
		String profileImageUrl,
		String profileImageName
	) {
		return new UpdateProfileImageResponse(profileImageUrl, profileImageName);
	}
}
