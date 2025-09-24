package com.gomo.app.support.auth.presentation.response;

import java.util.UUID;

import com.gomo.app.support.auth.domain.model.AuthToken;
import com.gomo.app.core.member.domain.model.OAuthUserInfo;

import lombok.Getter;

@Getter
public class OAuthTokenResponse {

	private UUID memberId;
	private AuthToken authToken;
	private long expiresIn;
	private OAuthUserInfo userInfo;

	private OAuthTokenResponse(UUID memberId, AuthToken authToken, long expiresIn, OAuthUserInfo userInfo) {
		this.memberId = memberId;
		this.authToken = authToken;
		this.expiresIn = expiresIn;
		this.userInfo = userInfo;
	}

	public static OAuthTokenResponse of(UUID memberId, AuthToken authToken, long expiresIn, OAuthUserInfo userInfo) {
		return new OAuthTokenResponse(memberId, authToken, expiresIn, userInfo);
	}
}
