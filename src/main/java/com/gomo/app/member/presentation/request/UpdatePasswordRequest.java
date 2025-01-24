package com.gomo.app.member.presentation.request;

import lombok.Getter;

@Getter
public class UpdatePasswordRequest {

	private String originPassword;
	private String updatedPassword;

	private UpdatePasswordRequest(
		String originPassword,
		String updatedPassword
	) {
		this.originPassword = originPassword;
		this.updatedPassword = updatedPassword;
	}

	public static UpdatePasswordRequest of(
		String originPassword,
		String updatedPassword
	) {
		return new UpdatePasswordRequest(originPassword, updatedPassword);
	}
}
