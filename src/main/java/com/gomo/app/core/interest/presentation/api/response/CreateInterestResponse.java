package com.gomo.app.core.interest.presentation.api.response;

import java.util.UUID;

import lombok.Getter;

@Getter
public class CreateInterestResponse {

	private UUID id;

	private CreateInterestResponse(UUID id) {
		this.id = id;
	}

	public static CreateInterestResponse of(UUID id) {
		return new CreateInterestResponse(id);
	}
}
