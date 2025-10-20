package com.gomo.app.core.member.presentation.response;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CreateEmailCodeResponse {

	private final String code;

	private CreateEmailCodeResponse(String code) {
		this.code = code;
	}

	public static CreateEmailCodeResponse of(String code) {
		return new CreateEmailCodeResponse(code);
	}
}
