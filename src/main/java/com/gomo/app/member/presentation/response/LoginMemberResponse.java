package com.gomo.app.member.presentation.response;

import java.util.UUID;

import com.gomo.app.member.domain.model.Email;
import com.gomo.app.member.domain.model.Handle;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.model.MemberName;

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

	public static LoginMemberResponse of(MemberId memberId, String  accessToken, String refreshToken, long expiresIn) {
		return new LoginMemberResponse(memberId.getId(), accessToken, refreshToken, expiresIn);
	}
}
