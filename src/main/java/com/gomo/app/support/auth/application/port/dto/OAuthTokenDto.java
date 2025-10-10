package com.gomo.app.support.auth.application.port.dto;

import java.util.UUID;

public record OAuthTokenDto(UUID principalId, String accessToken, String refreshToken, long expiresIn, String loginProvider, String email, String name) {

	public static OAuthTokenDto of(UUID principalId, String accessToken, String refreshToken, long expiresIn, String loginProvider, String email, String name) {
		return new OAuthTokenDto(principalId, accessToken, refreshToken, expiresIn, loginProvider, email, name);
	}
}
