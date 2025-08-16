package com.gomo.app.auth.presentation.response;

import java.util.UUID;

import com.gomo.app.auth.domain.model.AuthToken;
import com.gomo.app.member.domain.model.OAuthUserInfo;

import lombok.Getter;

@Getter
public class OAuthResponse {
	private String accessToken;
	private OAuthUserInfo oauth;

	private OAuthResponse(String accessToken, OAuthUserInfo oauth) {
		this.accessToken = accessToken;
		this.oauth = oauth;
	}

	public static OAuthResponse of(String accessToken, OAuthUserInfo oauth) {
		return new OAuthResponse(accessToken, oauth);
	}
}
