package com.gomo.app.interest.presentation.response;

import java.util.UUID;

import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.MajorInterest;

import lombok.Getter;

@Getter
public class ReadMajorInterestResponse {

	private UUID id;
	private UUID interestId;
	private String name;
	private String logoUrl;
	private int level;
	private int score;
	private int scoreThreshold;
	private int displayOrder;

	private ReadMajorInterestResponse(
		UUID id,
		UUID interestId,
		String name,
		String logoUrl,
		int level,
		int score,
		int scoreThreshold,
		int displayOrder
	) {
		this.id = id;
		this.interestId = interestId;
		this.name = name;
		this.logoUrl = logoUrl;
		this.level = level;
		this.score = score;
		this.scoreThreshold = scoreThreshold;
		this.displayOrder = displayOrder;
	}

	public static ReadMajorInterestResponse of(MajorInterest majorInterest, Interest interest) {
		return new ReadMajorInterestResponse(
			majorInterest.getId().getId(),
			interest.getId().getId(),
			interest.getName().toString(),
			interest.getLogo().getUrl(),
			interest.getProficiency().getLevel().getLevel(),
			interest.getProficiency().getScore().getScore(),
			interest.getProficiency().getLevel().getScoreThreshold(),
			majorInterest.getDisplayOrder().getDisplayOrder()
		);
	}
}
