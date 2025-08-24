package com.gomo.app.auth.presentation.response;

import com.gomo.app.member.domain.model.OAuthUserInfo;

import lombok.Getter;

@Getter
public class OAuthResponse {
	private String accessToken;
	private OAuthUserInfo userInfo;

	private OAuthResponse(String accessToken, OAuthUserInfo userInfo) {
		this.accessToken = accessToken;
		this.userInfo = userInfo;
	}

	public static OAuthResponse of(String accessToken, OAuthUserInfo userInfo) {
		return new OAuthResponse(accessToken, userInfo);
	}
}
