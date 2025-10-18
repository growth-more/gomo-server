package com.gomo.app.support.auth.application.port.dto;

import java.util.UUID;

public record OAuthTokenDto(UUID principalId, String accessToken, String refreshToken, long expiresIn, String loginProvider, String email, String name) {

	public static OAuthTokenDto withToken(UUID principalId, String accessToken, String refreshToken, long expiresIn, String loginProvider, String email, String name) {
		return new OAuthTokenDto(principalId, accessToken, refreshToken, expiresIn, loginProvider, email, name);
	}

	public static OAuthTokenDto withoutToken(String loginProvider, String email, String name) {
		return new OAuthTokenDto(null, null, null, 0L, loginProvider, email, name);
	}
}
