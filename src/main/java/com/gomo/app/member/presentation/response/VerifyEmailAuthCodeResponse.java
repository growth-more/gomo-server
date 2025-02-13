package com.gomo.app.member.presentation.response;

import lombok.Getter;

@Getter
public class VerifyEmailAuthCodeResponse {
    private String email;

	private VerifyEmailAuthCodeResponse(String email) {
		this.email = email;
	}

	public static VerifyEmailAuthCodeResponse of(String email) {
		return new VerifyEmailAuthCodeResponse(email);
	}
}
