package com.gomo.app.support.auth.domain.model;

import com.gomo.app.common.arch.ValueObject;
import com.gomo.app.core.member.domain.model.LoginProvider;

import lombok.Getter;

@Getter
@ValueObject
public class OAuthPrincipal {

	private final LoginProvider provider;
	private final String email;
	private final String name;

	private OAuthPrincipal(LoginProvider provider, String email, String name) {
		this.provider = provider;
		this.email = email;
		this.name = name;
	}

	public static OAuthPrincipal of(LoginProvider provider, String email, String name) {
		return new OAuthPrincipal(provider, email, name);
	}
}
