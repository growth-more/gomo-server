package com.gomo.app.interest.presentation.response;

import java.util.UUID;

import com.gomo.app.interest.application.port.dto.MajorInterestDto;

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

	public static ReadMajorInterestResponse of(MajorInterestDto dto) {
		return new ReadMajorInterestResponse(dto.id(), dto.interestId(), dto.name(), dto.logoUrl(), dto.level(), dto.score(), dto.scoreThreshold(), dto.displayOrder());
	}
}
