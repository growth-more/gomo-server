package com.gomo.app.core.auth.adapter.in.api.response;

import java.util.UUID;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class CreatePrincipalResponse {

	private final UUID id;

	private CreatePrincipalResponse(UUID id) {
		this.id = id;
	}

	public static CreatePrincipalResponse of(UUID id) {
		return new CreatePrincipalResponse(id);
	}
}
