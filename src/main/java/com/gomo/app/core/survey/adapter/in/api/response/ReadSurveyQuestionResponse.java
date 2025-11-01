package com.gomo.app.core.survey.adapter.in.api.response;

import java.util.List;
import java.util.UUID;

import com.gomo.app.core.survey.application.dto.SurveyQuestionDto;

import lombok.Getter;

@Getter
public class ReadSurveyQuestionResponse {

	private UUID id;
	private String questionSelectType;
	private boolean isRequired;
	private String content;
	private List<ReadSurveyItemResponse> surveyItems;

	private ReadSurveyQuestionResponse(UUID id, String questionSelectType, boolean isRequired, String content, List<ReadSurveyItemResponse> surveyItems) {
		this.id = id;
		this.questionSelectType = questionSelectType;
		this.isRequired = isRequired;
		this.content = content;
		this.surveyItems = surveyItems;
	}

	public static ReadSurveyQuestionResponse from(SurveyQuestionDto dto) {
		return new ReadSurveyQuestionResponse(
			dto.id(),
			dto.questionSelectType(),
			dto.isRequired(),
			dto.content(),
			dto.surveyItems().stream().map(ReadSurveyItemResponse::from).toList()
		);
	}
}
