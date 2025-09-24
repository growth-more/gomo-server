package com.gomo.app.core.member.presentation.request;

import lombok.Getter;

@Getter
public class UpdatePasswordRequest {

	private String originPassword;
	private String newPassword;

	private UpdatePasswordRequest(
		String originPassword,
		String newPassword
	) {
		this.originPassword = originPassword;
		this.newPassword = newPassword;
	}

	public static UpdatePasswordRequest of(
		String originPassword,
		String updatedPassword
	) {
		return new UpdatePasswordRequest(originPassword, updatedPassword);
	}
}
