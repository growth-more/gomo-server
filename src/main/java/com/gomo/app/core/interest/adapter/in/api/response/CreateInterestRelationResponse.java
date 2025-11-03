package com.gomo.app.core.interest.adapter.in.api.response;

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
