package com.gomo.app.interest.presentation.response;

import java.util.UUID;

import com.gomo.app.interest.domain.model.InterestRelationId;

import lombok.Getter;

@Getter
public class CreateInterestRelationResponse {

	private UUID id;

	private CreateInterestRelationResponse(UUID id) {
		this.id = id;
	}

	public static CreateInterestRelationResponse of(InterestRelationId id) {
		return new CreateInterestRelationResponse(id.getId());
	}
}
