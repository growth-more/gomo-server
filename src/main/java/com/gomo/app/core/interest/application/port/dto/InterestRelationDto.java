package com.gomo.app.core.interest.application.port.dto;

import java.util.UUID;

import com.gomo.app.core.interest.domain.model.InterestRelation;

public record InterestRelationDto(UUID id, UUID registrantId, UUID parentInterestId, UUID childInterestId) {

	public static InterestRelationDto from(InterestRelation interestRelation) {
		return new InterestRelationDto(
			interestRelation.id(),
			interestRelation.registrantId(),
			interestRelation.parentId(),
			interestRelation.childId()
		);
	}
}
