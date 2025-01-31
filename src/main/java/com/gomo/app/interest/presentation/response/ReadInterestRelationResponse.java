package com.gomo.app.interest.presentation.response;

import java.util.UUID;

import com.gomo.app.interest.domain.model.InterestRelation;

import lombok.Getter;

@Getter
public class ReadInterestRelationResponse {

	private UUID id;
	private UUID registrantId;
	private UUID parentInterestId;
	private UUID childInterestId;

	private ReadInterestRelationResponse(
		UUID id,
		UUID registrantId,
		UUID parentInterestId,
		UUID childInterestId
	) {
		this.id = id;
		this.registrantId = registrantId;
		this.parentInterestId = parentInterestId;
		this.childInterestId = childInterestId;
	}

	public static ReadInterestRelationResponse of(InterestRelation interestRelation) {
		return new ReadInterestRelationResponse(
			interestRelation.getId().getId(),
			interestRelation.getRegistrantId().getId(),
			interestRelation.getParentInterestId().getId(),
			interestRelation.getChildInterestId().getId()
		);
	}
}
