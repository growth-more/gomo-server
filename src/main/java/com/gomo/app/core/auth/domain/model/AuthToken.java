package com.gomo.app.core.auth.domain.model;

import com.gomo.app.common.arch.ValueObject;

import lombok.Getter;

@Getter
@ValueObject
public class AuthToken {

	private final String accessToken;
	private final String refreshToken;

	private AuthToken(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public static AuthToken of(String accessToken, String refreshToken) {
		return new AuthToken(accessToken, refreshToken);
	}
}
