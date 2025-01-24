package com.gomo.app.interest.presentation.request;

import java.util.UUID;

import lombok.Getter;

@Getter
public class CreateInterestRelationRequest {

	private UUID registrantId;
	private UUID parentInterestId;
	private UUID childInterestId;

	private CreateInterestRelationRequest(
		UUID registrantId,
		UUID parentInterestId,
		UUID childInterestId
	) {
		this.registrantId = registrantId;
		this.parentInterestId = parentInterestId;
		this.childInterestId = childInterestId;
	}

	public static CreateInterestRelationRequest of(
		UUID registrantId,
		UUID parentInterestId,
		UUID childInterestId
	) {
		return new CreateInterestRelationRequest(registrantId, parentInterestId, childInterestId);
	}
}
