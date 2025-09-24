package com.gomo.app.interest.presentation.response;

import java.util.UUID;

import lombok.Getter;

@Getter
public class CreateInterestRelationResponse {

	private UUID id;

	private CreateInterestRelationResponse(UUID id) {
		this.id = id;
	}

	public static CreateInterestRelationResponse of(UUID id) {
		return new CreateInterestRelationResponse(id);
	}
}
