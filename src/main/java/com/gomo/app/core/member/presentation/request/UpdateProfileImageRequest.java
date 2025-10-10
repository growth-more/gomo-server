package com.gomo.app.core.member.presentation.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;

@Getter
public class UpdateProfileImageRequest {

	private final MultipartFile profileImage;

	public UpdateProfileImageRequest(MultipartFile profileImage) {
		this.profileImage = profileImage;
	}

	public static UpdateProfileImageRequest of(MultipartFile profileImage) {
		return new UpdateProfileImageRequest(profileImage);
	}
}
