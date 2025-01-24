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
	private String email;
	private String handle;
	private String name;

	private LoginMemberResponse(UUID id, String email, String handle, String name) {
		this.id = id;
		this.email = email;
		this.handle = handle;
		this.name = name;
	}

	public static LoginMemberResponse of(MemberId memberId, Email email, Handle handle, MemberName name) {
		return new LoginMemberResponse(memberId.getId(), email.toString(), handle.toString(), name.toString());
	}
}
