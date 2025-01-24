package com.gomo.app.member.presentation.request;

import com.gomo.app.member.domain.model.Email;
import com.gomo.app.member.domain.model.Handle;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.model.MemberName;
import com.gomo.app.member.domain.model.Motto;
import com.gomo.app.member.domain.model.Password;

import lombok.Getter;

@Getter
public class CreateMemberRequest {

	private String email;
	private String password;
	private String handle;
	private String name;
	private String motto;

	private CreateMemberRequest() {
	}

	private CreateMemberRequest(
		String email,
		String password,
		String handle,
		String name,
		String motto
	) {
		this.email = email;
		this.password = password;
		this.handle = handle;
		this.name = name;
		this.motto = motto;
	}

	public static CreateMemberRequest of(
		String email,
		String password,
		String handle,
		String name,
		String motto
	) {
		return new CreateMemberRequest(email, password, handle, name, motto);
	}

	public Member toDomain(MemberId memberId) {
		return Member.of(memberId, Email.of(email), Password.of(password), Handle.of(handle), MemberName.of(name), Motto.of(motto));
	}
}
