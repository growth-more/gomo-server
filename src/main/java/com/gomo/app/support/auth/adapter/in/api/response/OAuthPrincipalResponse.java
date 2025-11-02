package com.gomo.app.support.auth.adapter.in.api.response;

import lombok.Getter;

@Getter
public class OAuthPrincipalResponse {
	private final String loginProvider;
	private final String email;
	private final String name;

	private OAuthPrincipalResponse(String loginProvider, String email, String name) {
		this.loginProvider = loginProvider;
		this.email = email;
		this.name = name;
	}

	public static OAuthPrincipalResponse of(String loginProvider, String email, String name) {
		return new OAuthPrincipalResponse(loginProvider, email, name);
	}

}
