package com.gomo.app.interest.application.port.dto;

import java.util.UUID;

import com.gomo.app.interest.domain.model.InterestRelation;

public record InterestRelationDto(UUID id, UUID registrantId, UUID parentInterestId, UUID childInterestId) {

	public static InterestRelationDto from(InterestRelation interestRelation) {
		return new InterestRelationDto(
			interestRelation.uuid(),
			interestRelation.registrantUuid(),
			interestRelation.parentUuid(),
			interestRelation.childUuid()
		);
	}
}
