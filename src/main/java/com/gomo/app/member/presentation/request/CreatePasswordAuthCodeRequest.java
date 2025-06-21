package com.gomo.app.member.presentation.request;

import lombok.Getter;

@Getter
public class CreatePasswordAuthCodeRequest {
	private String email;

	private CreatePasswordAuthCodeRequest(String email) {
		this.email = email;
	}

	public static CreatePasswordAuthCodeRequest of(String email) {
		return new CreatePasswordAuthCodeRequest(email);
	}
}
