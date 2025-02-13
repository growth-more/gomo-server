package com.gomo.app.member.exception;

import lombok.Getter;

@Getter
public enum MemberErrorCode {

	NOT_FOUND("Member not found with id: ", 404),
	EMAIL_DUPLICATED("Email already exists: ", 409),
	HANDLE_DUPLICATED("Handle already exists: ", 409),
	AUTHENTICATION_FAILED("Member Authentication fail", 401),
	PROFILE_IMAGE_TOO_LARGE("Profile image size too large", 413),
	QUEST_PROPERTY_UPDATE_NOT_ALLOWED("Quest property must be within the range, but: ", 422),
	MEMBER_BANNED("Member banned", 403),
	MEMBER_DELETED("Member deleted", 410);

	private final String message;
	private final int httpStatus;

	MemberErrorCode(String message, int httpStatus) {
		this.message = message;
		this.httpStatus = httpStatus;
	}
}
