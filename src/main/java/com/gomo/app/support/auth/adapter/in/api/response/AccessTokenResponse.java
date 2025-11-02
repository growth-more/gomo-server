package com.gomo.app.support.auth.adapter.in.api.response;

import java.util.UUID;

import lombok.Getter;

@Getter
public class AccessTokenResponse {
	private final UUID principalId;
	private final String accessToken;

	private AccessTokenResponse(UUID principalId, String accessToken) {
		this.principalId = principalId;
		this.accessToken = accessToken;
	}

	public static AccessTokenResponse of(UUID principalId, String accessToken) {
		return new AccessTokenResponse(principalId, accessToken);
	}

}
