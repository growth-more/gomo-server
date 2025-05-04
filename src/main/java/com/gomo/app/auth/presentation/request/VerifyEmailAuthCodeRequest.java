package com.gomo.app.auth.presentation.request;

import lombok.Getter;

@Getter
public class VerifyEmailAuthCodeRequest {
	private String email;
	private String code;

	private VerifyEmailAuthCodeRequest(String email, String code) {
		this.email = email;
		this.code = code;
	}

	public static VerifyEmailAuthCodeRequest of(String email, String code) {
		return new VerifyEmailAuthCodeRequest(email, code);
	}
}
