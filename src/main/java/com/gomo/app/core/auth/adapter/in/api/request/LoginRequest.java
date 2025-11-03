package com.gomo.app.core.auth.adapter.in.api.request;

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
