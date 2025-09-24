package com.gomo.app.support.auth.domain.model;

import com.gomo.app.common.ValueObject;

import lombok.Getter;

@Getter
@ValueObject
public class AuthToken {

	private String accessToken;
	private String refreshToken;

	protected AuthToken() {
	}

	private AuthToken(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public static AuthToken of(String accessToken, String refreshToken) {
		return new AuthToken(accessToken, refreshToken);
	}
}
