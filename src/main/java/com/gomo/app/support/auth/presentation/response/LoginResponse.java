package com.gomo.app.support.auth.presentation.response;

import java.util.UUID;

import lombok.Getter;

@Getter
public class LoginResponse {
	private UUID memberId;
	private String token;

	private LoginResponse(UUID memberId, String token) {
		this.memberId = memberId;
		this.token = token;
	}

	public static LoginResponse of(UUID memberId, String token) {
		return new LoginResponse(memberId, token);
	}

}
