package com.gomo.app.interest.presentation.response;

import java.util.UUID;

import com.gomo.app.interest.application.port.dto.InterestDto;

import lombok.Getter;

@Getter
public class ReadInterestResponse {

	private final UUID id;
	private final UUID registrantId;
	private final String name;
	private final String logoUrl;
	private final String colorCode;
	private final int level;
	private final int score;
	private final int scoreThreshold;
	private final int totalScore;
	private final UUID majorInterestId;

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

	public static ReadInterestResponse from(InterestDto interestdto) {
		return new ReadInterestResponse(
			interestdto.id(),
			interestdto.registrantId(),
			interestdto.name(),
			interestdto.logoUrl(),
			interestdto.colorCode(),
			interestdto.proficiency().level(),
			interestdto.proficiency().score(),
			interestdto.proficiency().scoreThreshold(),
			interestdto.proficiency().totalScore(),
			interestdto.majorInterestId()
		);
	}
}
