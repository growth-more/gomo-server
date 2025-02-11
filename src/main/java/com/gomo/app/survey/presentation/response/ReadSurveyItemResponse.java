package com.gomo.app.survey.presentation.response;

import java.util.UUID;

import com.gomo.app.survey.domain.model.SurveyItem;

import lombok.Getter;

@Getter
public class ReadSurveyItemResponse {

	private UUID id;
	private String content;
	private int displayOrder;

	private ReadSurveyItemResponse(
		UUID id,
		String content,
		int displayOrder
	) {
		this.id = id;
		this.content = content;
		this.displayOrder = displayOrder;
	}

	public static ReadSurveyItemResponse of(SurveyItem surveyItem) {
		return new ReadSurveyItemResponse(surveyItem.getId().getId(), surveyItem.getContent(), surveyItem.getDisplayOrder().getDisplayOrder());
	}
}
