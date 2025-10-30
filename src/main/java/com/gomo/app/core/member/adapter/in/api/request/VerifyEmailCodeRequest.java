package com.gomo.app.core.member.adapter.in.api.request;

import lombok.Getter;

@Getter
public class VerifyEmailCodeRequest {
	private final String email;
	private final String code;

	private VerifyEmailCodeRequest(String email, String code) {
		this.email = email;
		this.code = code;
	}

	public static VerifyEmailCodeRequest of(String email, String code) {
		return new VerifyEmailCodeRequest(email, code);
	}
}
