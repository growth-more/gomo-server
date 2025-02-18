package com.gomo.app.member.presentation.response;

import com.gomo.app.member.domain.model.Email;
import lombok.Getter;

@Getter
public class CreateEmailAuthCodeResponse {

	private String email;

	private CreateEmailAuthCodeResponse(String email) {
		this.email = email;
	}

	public static CreateEmailAuthCodeResponse of(String email) {
		return new CreateEmailAuthCodeResponse(email);
	}
}
