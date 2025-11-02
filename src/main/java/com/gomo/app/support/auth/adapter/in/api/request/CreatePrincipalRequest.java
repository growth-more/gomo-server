package com.gomo.app.support.auth.adapter.in.api.request;

import com.gomo.app.support.auth.application.port.command.CreatePrincipalCommand;

import lombok.Getter;

@Getter
public class CreatePrincipalRequest {

	private final String email;
	private final String password;
	private final String handle;
	private final String name;
	private final String motto;
	private final String loginProvider;
	private final String temporaryToken;

	private CreatePrincipalRequest(String email, String password, String handle, String name, String motto, String loginProvider, String temporaryToken) {
		this.email = email;
		this.password = password;
		this.handle = handle;
		this.name = name;
		this.motto = motto;
		this.loginProvider = loginProvider;
		this.temporaryToken = temporaryToken;
	}

	public static CreatePrincipalRequest of(String email, String password, String handle, String name, String motto, String loginProvider, String temporaryToken) {
		return new CreatePrincipalRequest(email, password, handle, name, motto, loginProvider, temporaryToken);
	}

	public CreatePrincipalCommand toCommand() {
		return CreatePrincipalCommand.of(email, password, handle, name, motto, loginProvider, temporaryToken);
	}
}
