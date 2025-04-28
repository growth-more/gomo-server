package com.gomo.app.member.presentation.response;

import java.util.UUID;

import lombok.Getter;

@Getter
public class LoginMemberResponse {

	private UUID id;
	private String accessToken;
	private String refreshToken;
	private long expiresIn;

	private LoginMemberResponse(UUID id, String accessToken, String refreshToken, long expiresIn) {
		this.id = id;
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.expiresIn = expiresIn;
	}

	public static LoginMemberResponse of(UUID memberId, String  accessToken, String refreshToken, long expiresIn) {
		return new LoginMemberResponse(memberId, accessToken, refreshToken, expiresIn);
	}
}
