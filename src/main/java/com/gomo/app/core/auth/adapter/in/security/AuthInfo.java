package com.gomo.app.core.auth.adapter.in.security;

import java.util.UUID;

import lombok.Getter;

@Getter
public class AuthInfo {

	private UUID principalId;

	private AuthInfo(UUID principalId) {
		this.principalId = principalId;
	}

	public static AuthInfo of(UUID principalId) {
		return new AuthInfo(principalId);
	}
}
