package com.gomo.app.core.member.application.port.command;

public record CreateMemberCommand(String email, String rawPassword, String handle, String name, String motto, String loginProvider, String temporaryToken) {

	public static CreateMemberCommand of(String email, String rawPassword, String handle, String name, String motto, String loginProvider, String temporaryToken) {
		return new CreateMemberCommand(email, rawPassword, handle, name, motto, loginProvider, temporaryToken);
	}
}
