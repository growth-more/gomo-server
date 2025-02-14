package com.gomo.app.interest.presentation.request;

import java.util.UUID;

import lombok.Getter;

@Getter
public class CreateInterestRelationRequest {

	private UUID parentInterestId;
	private UUID childInterestId;

	private CreateInterestRelationRequest(
		UUID parentInterestId,
		UUID childInterestId
	) {
		this.parentInterestId = parentInterestId;
		this.childInterestId = childInterestId;
	}

	public static CreateInterestRelationRequest of(
		UUID parentInterestId,
		UUID childInterestId
	) {
		return new CreateInterestRelationRequest(parentInterestId, childInterestId);
	}
}
