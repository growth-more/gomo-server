package com.gomo.app.interest.presentation.response;

import java.util.UUID;

import lombok.Getter;

@Getter
public class CreateInterestResponse {

	private UUID id;

	private CreateInterestResponse(UUID id) {
		this.id = id;
	}

	public static CreateInterestResponse of(UUID interestId) {
		return new CreateInterestResponse(interestId);
	}
}
