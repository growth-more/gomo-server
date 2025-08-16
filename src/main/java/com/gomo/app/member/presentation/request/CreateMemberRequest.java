package com.gomo.app.member.presentation.request;

import com.gomo.app.member.domain.model.Email;
import com.gomo.app.member.domain.model.Handle;
import com.gomo.app.member.domain.model.LoginProvider;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.model.MemberName;
import com.gomo.app.member.domain.model.Motto;
import com.gomo.app.member.domain.model.Password;
import com.gomo.app.member.domain.service.PasswordService;

import lombok.Getter;

@Getter
public class CreateMemberRequest {

	private String email;
	private String password;
	private String handle;
	private String name;
	private String motto;
	private LoginProvider loginProvider;

	private CreateMemberRequest() {
	}

	private CreateMemberRequest(
		String email,
		String password,
		String handle,
		String name,
		String motto,
		LoginProvider loginProvider
	) {
		this.email = email;
		this.password = password;
		this.handle = handle;
		this.name = name;
		this.motto = motto;
		this.loginProvider = loginProvider;
	}

	public static CreateMemberRequest of(
		String email,
		String password,
		String handle,
		String name,
		String motto,
		LoginProvider loginProvider
	) {
		return new CreateMemberRequest(email, password, handle, name, motto, loginProvider);
	}

	public Member toDomain(MemberId memberId, LoginProvider loginProvider, PasswordService passwordService) {
		Password newPw = Password.ofRaw(password);
		return Member.of(memberId, Email.of(email), newPw.encodedWith(passwordService), Handle.of(handle),
			MemberName.of(name), Motto.of(motto), loginProvider);
	}
}
