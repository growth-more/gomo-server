package com.gomo.app.core.survey.adapter.in.api.response;

import java.util.UUID;

import com.gomo.app.core.survey.application.dto.SurveyItemDto;

import lombok.Getter;

@Getter
public class ReadSurveyItemResponse {

	private UUID id;
	private String content;
	private int displayOrder;

	private ReadSurveyItemResponse(UUID id, String content, int displayOrder) {
		this.id = id;
		this.content = content;
		this.displayOrder = displayOrder;
	}

	public static ReadSurveyItemResponse from(SurveyItemDto dto) {
		return new ReadSurveyItemResponse(dto.id(), dto.content(), dto.displayOrder());
	}
}
