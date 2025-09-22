package com.gomo.app.member.presentation.request;

import com.gomo.app.member.application.port.command.CreateMemberCommand;

import lombok.Getter;

@Getter
public class CreateMemberRequest {

	private String email;
	private String rawPassword;
	private String handle;
	private String name;
	private String motto;
	private String loginProvider;

	private CreateMemberRequest() {
	}

	private CreateMemberRequest(
		String email,
		String rawPassword,
		String handle,
		String name,
		String motto,
		String loginProvider
	) {
		this.email = email;
		this.rawPassword = rawPassword;
		this.handle = handle;
		this.name = name;
		this.motto = motto;
		this.loginProvider = loginProvider;
	}

	public static CreateMemberRequest of(
		String email,
		String rawPassword,
		String handle,
		String name,
		String motto,
		String loginProvider
	) {
		return new CreateMemberRequest(email, rawPassword, handle, name, motto, loginProvider);
	}

	public CreateMemberCommand toCommand() {
		return CreateMemberCommand.of(email, rawPassword, handle, name, motto, loginProvider);
	}
}
