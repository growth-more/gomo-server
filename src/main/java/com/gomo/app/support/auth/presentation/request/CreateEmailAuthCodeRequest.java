package com.gomo.app.support.auth.presentation.request;

import lombok.Getter;

@Getter
public class CreateEmailAuthCodeRequest {
	private final String email;

	private CreateEmailAuthCodeRequest(String email) {
		this.email = email;
	}

	public static CreateEmailAuthCodeRequest of(String email) {
		return new CreateEmailAuthCodeRequest(email);
	}
}
