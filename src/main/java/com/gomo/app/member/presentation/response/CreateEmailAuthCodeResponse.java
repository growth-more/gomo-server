package com.gomo.app.member.presentation.response;

import lombok.Getter;

@Getter
public class CreateEmailAuthCodeResponse {

	private String code;

	private CreateEmailAuthCodeResponse(String code) {
		this.code = code;
	}

	public static CreateEmailAuthCodeResponse of(String code) {
		return new CreateEmailAuthCodeResponse(code);
	}
}
