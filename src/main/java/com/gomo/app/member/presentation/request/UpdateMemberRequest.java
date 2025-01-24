package com.gomo.app.member.presentation.request;

import lombok.Getter;

@Getter
public class UpdateMemberRequest {

	private String name;
	private String motto;

	private UpdateMemberRequest(
		String name,
		String motto
	) {
		this.name = name;
		this.motto = motto;
	}

	public static UpdateMemberRequest of(
		String name,
		String motto
	) {
		return new UpdateMemberRequest(name, motto);
	}
}
