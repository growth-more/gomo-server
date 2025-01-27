package com.gomo.app.survey.presentation.response;

import com.gomo.app.common.domain.service.DisplayOrder;
import com.gomo.app.survey.domain.model.SurveyItemId;

import lombok.Getter;

@Getter
public class ReadSurveyItemResponse {

	private SurveyItemId surveyItemId;
	private String content;
	private DisplayOrder displayOrder;

	private ReadSurveyItemResponse(
		SurveyItemId surveyItemId,
		String content,
		DisplayOrder displayOrder
	) {
		this.surveyItemId = surveyItemId;
		this.content = content;
		this.displayOrder = displayOrder;
	}

	public static ReadSurveyItemResponse of(
		SurveyItemId surveyItemId,
		String content,
		DisplayOrder displayOrder
	) {
		return new ReadSurveyItemResponse(surveyItemId, content, displayOrder);
	}
}
