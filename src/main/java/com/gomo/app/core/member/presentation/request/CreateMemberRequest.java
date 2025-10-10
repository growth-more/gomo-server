package com.gomo.app.core.member.presentation.request;

import com.gomo.app.core.member.application.port.command.CreateMemberCommand;

import lombok.Getter;

@Getter
public class CreateMemberRequest {

	private final String email;
	private final String rawPassword;
	private final String handle;
	private final String name;
	private final String motto;
	private final String loginProvider;
	private final String temporaryToken;

	private CreateMemberRequest(String email, String rawPassword, String handle, String name, String motto, String loginProvider, String temporaryToken) {
		this.email = email;
		this.rawPassword = rawPassword;
		this.handle = handle;
		this.name = name;
		this.motto = motto;
		this.loginProvider = loginProvider;
		this.temporaryToken = temporaryToken;
	}

	public static CreateMemberRequest of(String email, String rawPassword, String handle, String name, String motto, String loginProvider, String temporaryToken) {
		return new CreateMemberRequest(email, rawPassword, handle, name, motto, loginProvider, temporaryToken);
	}

	public CreateMemberCommand toCommand() {
		return CreateMemberCommand.of(email, rawPassword, handle, name, motto, loginProvider, temporaryToken);
	}
}
