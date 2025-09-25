package com.gomo.app.core.member.presentation.request;

import lombok.Getter;

@Getter
public class VerifyEmailCodeRequest {
	private String email;
	private String code;

	private VerifyEmailCodeRequest(String email, String code) {
		this.email = email;
		this.code = code;
	}

	public static VerifyEmailCodeRequest of(String email, String code) {
		return new VerifyEmailCodeRequest(email, code);
	}
}
