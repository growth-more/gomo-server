package com.gomo.app.member.presentation.request;

import com.gomo.app.member.domain.model.Email;
import lombok.Getter;

@Getter
public class CreateEmailAuthCodeRequest {

	private String email;

	private CreateEmailAuthCodeRequest(String email) {
		this.email = email;
	}

	public static CreateEmailAuthCodeRequest of(String email) {
		return new CreateEmailAuthCodeRequest(email);
	}
}
