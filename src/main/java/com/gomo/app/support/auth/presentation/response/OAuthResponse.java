package com.gomo.app.support.auth.presentation.response;

import com.gomo.app.support.auth.application.port.dto.OAuthTokenDto;

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
		if (oAuthTokenDto == null) {
			return null;
		}
		return new OAuthResponse(
			oAuthTokenDto.accessToken(), OAuthPrincipalResponse.of(oAuthTokenDto.loginProvider(), oAuthTokenDto.email(), oAuthTokenDto.name()));
	}

	public static OAuthResponse none() {
		return new OAuthResponse(null, null);
	}
}
