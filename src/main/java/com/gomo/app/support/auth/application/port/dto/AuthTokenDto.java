package com.gomo.app.support.auth.application.port.dto;

import java.util.UUID;

public record AuthTokenDto(UUID principalId, String accessToken, String refreshToken, long expiresIn) {

	public static AuthTokenDto of(UUID principalId, String accessToken, String refreshToken, long expiresIn) {
		return new AuthTokenDto(principalId, accessToken, refreshToken, expiresIn);
	}
}
