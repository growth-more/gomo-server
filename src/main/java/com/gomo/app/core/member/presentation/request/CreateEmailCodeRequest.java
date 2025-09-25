package com.gomo.app.core.member.presentation.request;

import lombok.Getter;

@Getter
public class CreateEmailCodeRequest {
	private final String email;

	private CreateEmailCodeRequest(String email) {
		this.email = email;
	}

	public static CreateEmailCodeRequest of(String email) {
		return new CreateEmailCodeRequest(email);
	}
}
