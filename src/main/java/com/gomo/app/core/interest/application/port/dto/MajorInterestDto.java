package com.gomo.app.core.interest.application.port.dto;

import java.util.UUID;

import com.gomo.app.core.interest.domain.model.Interest;
import com.gomo.app.core.interest.domain.model.MajorInterest;

public record MajorInterestDto(UUID id, UUID interestId, String name, String logoUrl, int level, int score, int scoreThreshold, int displayOrder) {

	public static MajorInterestDto of(UUID id, UUID interestId, String name, String logoUrl, int level, int score, int scoreThreshold, int displayOrder) {
		return new MajorInterestDto(id, interestId, name, logoUrl, level, score, scoreThreshold, displayOrder);
	}

	public static MajorInterestDto from(MajorInterest majorInterest, Interest interest) {
		return new MajorInterestDto(
			majorInterest.id(),
			interest.id(),
			interest.getName().toString(),
			interest.getLogo().getUrl(),
			interest.getProficiency().getLevel().getLevel(),
			interest.getProficiency().getScore().getScore(),
			interest.getProficiency().getLevel().getScoreThreshold(),
			majorInterest.getDisplayOrder().getDisplayOrder()
		);
	}
}
