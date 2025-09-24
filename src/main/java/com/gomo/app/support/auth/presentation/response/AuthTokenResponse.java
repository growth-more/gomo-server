package com.gomo.app.support.auth.presentation.response;

import java.util.UUID;

import com.gomo.app.support.auth.domain.model.AuthToken;

import lombok.Getter;

@Getter
public class AuthTokenResponse {

	private UUID memberId;
	private AuthToken authToken;
	private long expiresIn;

	private AuthTokenResponse(UUID memberId, AuthToken authToken, long expiresIn) {
		this.memberId = memberId;
		this.authToken = authToken;
		this.expiresIn = expiresIn;
	}

	public static AuthTokenResponse of(UUID memberId, AuthToken authToken, long expiresIn) {
		return new AuthTokenResponse(memberId, authToken, expiresIn);
	}
}
