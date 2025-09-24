package com.gomo.app.survey.presentation.response;

import java.util.UUID;

import com.gomo.app.survey.application.SurveyItemDto;

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
