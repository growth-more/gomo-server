package com.gomo.app.core.member.adapter.in.api.request;

import lombok.Getter;

@Getter
public class UpdatePasswordRequest {

	private final String originPassword;
	private final String newPassword;

	private UpdatePasswordRequest(String originPassword, String newPassword) {
		this.originPassword = originPassword;
		this.newPassword = newPassword;
	}

	public static UpdatePasswordRequest of(String originPassword, String updatedPassword) {
		return new UpdatePasswordRequest(originPassword, updatedPassword);
	}
}
