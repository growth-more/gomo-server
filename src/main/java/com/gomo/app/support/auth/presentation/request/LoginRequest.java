package com.gomo.app.support.auth.presentation.request;

import lombok.Getter;

@Getter
public class LoginRequest {

	private String email;
	private String password;

	private LoginRequest() {
	}

	private LoginRequest(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public static LoginRequest of(String email, String password) {
		return new LoginRequest(email, password);
	}
}
