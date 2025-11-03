package com.gomo.app.core.auth.adapter.in.api.response;

import com.gomo.app.core.auth.application.port.dto.OAuthTokenDto;

import lombok.Getter;

@Getter
public class OAuthResponse {
	private final String accessToken;
	private final OAuthPrincipalResponse principal;

	private OAuthResponse(String accessToken, OAuthPrincipalResponse principal) {
		this.accessToken = accessToken;
		this.principal = principal;
	}

	public static OAuthResponse from(OAuthTokenDto oAuthTokenDto) {
		if (oAuthTokenDto.accessToken() == null) {
			return new OAuthResponse(null, OAuthPrincipalResponse.of(oAuthTokenDto.loginProvider(), oAuthTokenDto.email(), oAuthTokenDto.name()));
		}
		return new OAuthResponse(
			oAuthTokenDto.accessToken(), OAuthPrincipalResponse.of(oAuthTokenDto.loginProvider(), oAuthTokenDto.email(), oAuthTokenDto.name()));
	}

	public static OAuthResponse none() {
		return new OAuthResponse(null, null);
	}
}
