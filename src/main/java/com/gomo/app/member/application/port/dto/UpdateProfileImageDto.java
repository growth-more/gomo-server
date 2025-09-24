package com.gomo.app.member.application.port.dto;

public record UpdateProfileImageDto(String profileImageUrl) {

	public static UpdateProfileImageDto of(String profileImageUrl) {
		return new UpdateProfileImageDto(profileImageUrl);
	}
}
