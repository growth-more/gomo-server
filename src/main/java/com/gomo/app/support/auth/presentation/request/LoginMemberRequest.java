package com.gomo.app.support.auth.presentation.request;

import lombok.Getter;

@Getter
public class LoginMemberRequest {

	private String email;
	private String password;

	private LoginMemberRequest() {
	}

	private LoginMemberRequest(String email, String password) {
		this.email = email;
		this.password = password;
	}

	public static LoginMemberRequest of(String email, String password) {
		return new LoginMemberRequest(email, password);
	}
}
