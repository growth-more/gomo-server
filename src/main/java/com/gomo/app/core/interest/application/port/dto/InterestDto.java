package com.gomo.app.core.interest.application.port.dto;

import java.util.UUID;

import com.gomo.app.core.interest.domain.model.Interest;

public record InterestDto(UUID id, UUID registrantId, Proficiency proficiency, String name, String logoUrl, String colorCode, UUID majorInterestId) {

	public static InterestDto of(Interest interest, UUID majorInterestId) {
		return new InterestDto(
			interest.uuid(),
			interest.registrantUuid(),
			Proficiency.of(
				interest.getProficiency().level(),
				interest.getProficiency().score(),
				interest.getProficiency().getLevel().getScoreThreshold(),
				interest.getProficiency().getTotalScore()
			),
			interest.getName().getInterestName(),
			interest.getLogo().getUrl(),
			interest.getColorCode(),
			majorInterestId
		);
	}

	public record Proficiency(int level, int score, int scoreThreshold, int totalScore) {

		public static Proficiency of(int level, int score, int scoreThreshold, int totalScore) {
			return new Proficiency(level, score, scoreThreshold, totalScore);
		}
	}
}
