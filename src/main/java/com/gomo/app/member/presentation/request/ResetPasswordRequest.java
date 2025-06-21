package com.gomo.app.member.presentation.request;

import lombok.Getter;

@Getter
public class ResetPasswordRequest {

	private String email;
	private String updatedPassword;

	private ResetPasswordRequest(
		String email,
		String updatedPassword
	) {
		this.email = email;
		this.updatedPassword = updatedPassword;
	}

	public static ResetPasswordRequest of(
		String email,
		String updatedPassword
	) {
		return new ResetPasswordRequest(email, updatedPassword);
	}
}
