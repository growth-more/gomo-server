package com.gomo.app.support.auth.presentation.response;

import java.util.UUID;

import lombok.Getter;

@Getter
public class LoginMemberResponse {
	private UUID memberId;
	private String token;

	private LoginMemberResponse(UUID memberId, String token) {
		this.memberId = memberId;
		this.token = token;
	}

	public static LoginMemberResponse of(UUID memberId, String token) {
		return new LoginMemberResponse(memberId, token);
	}

}
