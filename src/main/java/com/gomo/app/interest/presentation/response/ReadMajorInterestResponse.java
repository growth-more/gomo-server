package com.gomo.app.interest.presentation.response;

import java.util.UUID;

import lombok.Getter;

@Getter
public class ReadMajorInterestResponse {

	private UUID id;
	private String name;
	private String logoUrl;
	private int level;
	private int score;
	private int displayOrder;

	private ReadMajorInterestResponse(
		UUID id,
		String name,
		String logoUrl,
		int level,
		int score,
		int displayOrder
	) {
		this.id = id;
		this.name = name;
		this.logoUrl = logoUrl;
		this.level = level;
		this.score = score;
		this.displayOrder = displayOrder;
	}

	public static ReadMajorInterestResponse of(
		UUID id,
		String name,
		String logoUrl,
		int level,
		int score,
		int displayOrder
	) {
		return new ReadMajorInterestResponse(id, name, logoUrl, level, score, displayOrder);
	}
}
