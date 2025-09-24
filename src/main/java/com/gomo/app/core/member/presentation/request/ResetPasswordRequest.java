package com.gomo.app.core.member.presentation.request;

import lombok.Getter;

@Getter
public class ResetPasswordRequest {

	private String email;
	private String newPassword;

	private ResetPasswordRequest(
		String email,
		String newPassword
	) {
		this.email = email;
		this.newPassword = newPassword;
	}

	public static ResetPasswordRequest of(
		String email,
		String newPassword
	) {
		return new ResetPasswordRequest(email, newPassword);
	}
}
