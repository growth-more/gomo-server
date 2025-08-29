package com.gomo.app.interest.presentation.response;

import java.util.UUID;

import com.gomo.app.interest.domain.model.Interest;

import lombok.Getter;

@Getter
public class ReadInterestResponse {

	private UUID id;
	private UUID registrantId;
	private String name;
	private String logoUrl;
	private String colorCode;
	private int level;
	private int score;
	private int scoreThreshold;
	private int totalScore;
	private UUID majorInterestId;

	private ReadInterestResponse(
		UUID id,
		UUID registrantId,
		String name,
		String logoUrl,
		String colorCode,
		int level,
		int score,
		int scoreThreshold,
		int totalScore,
		UUID majorInterestId
	) {
		this.id = id;
		this.registrantId = registrantId;
		this.name = name;
		this.logoUrl = logoUrl;
		this.colorCode = colorCode;
		this.level = level;
		this.score = score;
		this.scoreThreshold = scoreThreshold;
		this.totalScore = totalScore;
		this.majorInterestId = majorInterestId;
	}

	public static ReadInterestResponse of(Interest interest, UUID majorInterestId) {
		return new ReadInterestResponse(
			interest.uuid(),
			interest.registrantUuid(),
			interest.getName().toString(),
			interest.getLogo().getUrl(),
			interest.getColorCode(),
			interest.getProficiency().getLevel().getLevel(),
			interest.getProficiency().getScore().getScore(),
			interest.getProficiency().getLevel().getScoreThreshold(),
			interest.getProficiency().getTotalScore(),
			majorInterestId);
	}
}
