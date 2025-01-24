package com.gomo.app.interest.presentation.response;

import java.util.UUID;

import lombok.Getter;

@Getter
public class ReadInterestResponse {

	private UUID id;
	private UUID memberId;
	private String name;
	private String logoUrl;
	private int level;
	private int score;
	private int totalScore;

	private ReadInterestResponse(
		UUID id,
		UUID memberId,
		String name,
		String logoUrl,
		int level,
		int score,
		int totalScore
	) {
		this.id = id;
		this.memberId = memberId;
		this.name = name;
		this.logoUrl = logoUrl;
		this.level = level;
		this.score = score;
		this.totalScore = totalScore;
	}

	public static ReadInterestResponse of(
		UUID id,
		UUID memberId,
		String name,
		String logoUrl,
		int level,
		int score,
		int totalScore
	) {
		return new ReadInterestResponse(id, memberId, name, logoUrl, level, score, totalScore);
	}
}
