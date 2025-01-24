package com.gomo.app.interest.presentation.response;

import java.util.UUID;

import lombok.Getter;

@Getter
public class ReadInterestRelationResponse {

	private UUID id;
	private UUID parentInterestId;
	private UUID childInterestId;

	private ReadInterestRelationResponse(
		UUID id,
		UUID parentInterestId,
		UUID childInterestId
	) {
		this.id = id;
		this.parentInterestId = parentInterestId;
		this.childInterestId = childInterestId;
	}

	public static ReadInterestRelationResponse of(
		UUID id,
		UUID parentInterestId,
		UUID childInterestId
	) {
		return new ReadInterestRelationResponse(id, parentInterestId, childInterestId);
	}
}
