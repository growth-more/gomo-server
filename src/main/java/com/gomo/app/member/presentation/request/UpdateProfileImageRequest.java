package com.gomo.app.member.presentation.request;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;

@Getter
public class UpdateProfileImageRequest {

	private MultipartFile profileImage;

	private UpdateProfileImageRequest(MultipartFile profileImage) {
		this.profileImage = profileImage;
	}

	public static UpdateProfileImageRequest of(MultipartFile profileImage) {
		return new UpdateProfileImageRequest(profileImage);
	}
}
