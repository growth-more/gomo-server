package com.gomo.app.core.member.adapter.in.api.response;

import lombok.Getter;

@Getter
public class VerifyEmailCodeResponse {

	private final String temporaryToken;

	private VerifyEmailCodeResponse(String temporaryToken) {
		this.temporaryToken = temporaryToken;
	}

	public static VerifyEmailCodeResponse of(String temporaryToken) {
		return new VerifyEmailCodeResponse(temporaryToken);
	}
}
