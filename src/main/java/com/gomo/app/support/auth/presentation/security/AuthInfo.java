package com.gomo.app.support.auth.presentation.security;

import lombok.Getter;

import java.util.UUID;

@Getter
public class AuthInfo {

	private UUID memberId;

	private AuthInfo(UUID memberId) {
		this.memberId = memberId;
	}

	public static AuthInfo of(UUID memberId) {
		return new AuthInfo(memberId);
	}
}
