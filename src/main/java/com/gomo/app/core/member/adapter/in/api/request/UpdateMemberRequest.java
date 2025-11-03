package com.gomo.app.core.member.adapter.in.api.request;

import lombok.Getter;

@Getter
public class UpdateMemberRequest {

	private final String name;
	private final String motto;

	private UpdateMemberRequest(String name, String motto) {
		this.name = name;
		this.motto = motto;
	}

	public static UpdateMemberRequest of(String name, String motto) {
		return new UpdateMemberRequest(name, motto);
	}
}
