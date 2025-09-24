package com.gomo.app.member.application.port.command;

public record CreateMemberCommand(String email, String rawPassword, String handle, String name, String motto, String loginProvider) {

	public static CreateMemberCommand of(String email, String rawPassword, String handle, String name, String motto, String loginProvider) {
		return new CreateMemberCommand(email, rawPassword, handle, name, motto, loginProvider);
	}
}
