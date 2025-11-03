package com.gomo.app.core.member.adapter.in.api.request;

import lombok.Getter;

@Getter
public class ResetPasswordRequest {

	private final String email;
	private final String newPassword;
	private final String temporaryToken;

	private ResetPasswordRequest(String email, String newPassword, String temporaryToken) {
		this.email = email;
		this.newPassword = newPassword;
		this.temporaryToken = temporaryToken;
	}

	public static ResetPasswordRequest of(String email, String newPassword, String temporaryToken) {
		return new ResetPasswordRequest(email, newPassword, temporaryToken);
	}
}
