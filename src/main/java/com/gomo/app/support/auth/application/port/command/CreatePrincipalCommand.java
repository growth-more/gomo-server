package com.gomo.app.support.auth.application.port.command;

import com.gomo.app.core.member.application.port.command.CreateMemberCommand;

public record CreatePrincipalCommand(String email, String password, String handle, String name, String motto, String loginProvider, String temporaryToken) {

	public static CreatePrincipalCommand of(String email, String password, String handle, String name, String motto, String loginProvider, String temporaryToken) {
		return new CreatePrincipalCommand(email, password, handle, name, motto, loginProvider, temporaryToken);
	}

	public CreateMemberCommand toMemberCommand() {
		return CreateMemberCommand.of(email, password, handle, name, motto, loginProvider);
	}
}
