package com.gomo.app.member.presentation.request;

import lombok.Getter;

@Getter
public class ResetPasswordRequest {

	private String email;
	private String resetPassword;

	private ResetPasswordRequest(
		String email,
		String resetPassword
	) {
		this.email = email;
		this.resetPassword = resetPassword;
	}

	public static ResetPasswordRequest of(
		String email,
		String resetPassword
	) {
		return new ResetPasswordRequest(email, resetPassword);
	}
}
