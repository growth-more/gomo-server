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
	private int level;
	private int score;
	private int scoreThreshold;
	private int totalScore;
	private boolean isMajorInterest;

	private ReadInterestResponse(
		UUID id,
		UUID registrantId,
		String name,
		String logoUrl,
		int level,
		int score,
		int scoreThreshold,
		int totalScore
	) {
		this.id = id;
		this.registrantId = registrantId;
		this.name = name;
		this.logoUrl = logoUrl;
		this.level = level;
		this.score = score;
		this.scoreThreshold = scoreThreshold;
		this.totalScore = totalScore;
	}

	public static ReadInterestResponse of(Interest interest) {
		return new ReadInterestResponse(
			interest.getId().getId(),
			interest.getRegistrantId().getId(),
			interest.getName().toString(),
			interest.getLogo().getUrl(),
			interest.getProficiency().getLevel().getLevel(),
			interest.getProficiency().getScore().getScore(),
			interest.getProficiency().getLevel().getScoreThreshold(),
			interest.getProficiency().getTotalScore());
	}

	public void updateMajorInterest() {
		this.isMajorInterest = true;
	}
}
