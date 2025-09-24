package com.gomo.app.core.survey.presentation.request;

import java.util.UUID;

import com.gomo.app.core.survey.application.SurveyItemDto;

import lombok.Getter;

@Getter
public class SelectedSurveyItemRequest {

	private UUID surveyQuestionId;
	private UUID surveyItemId;
	private String surveyItemContent;
	private String customAnswer;

	private SelectedSurveyItemRequest(UUID surveyQuestionId, UUID surveyItemId, String surveyItemContent, String customAnswer) {
		this.surveyQuestionId = surveyQuestionId;
		this.surveyItemId = surveyItemId;
		this.surveyItemContent = surveyItemContent;
		this.customAnswer = customAnswer;
	}

	public static SelectedSurveyItemRequest of(UUID surveyQuestionId, UUID surveyItemId, String surveyItemContent, String customAnswer) {
		return new SelectedSurveyItemRequest(surveyQuestionId, surveyItemId, surveyItemContent, customAnswer);
	}

	public SurveyItemDto toSurveyItemDto() {
		return SurveyItemDto.withCustomAnswer(surveyItemId, surveyQuestionId, surveyItemContent, null, customAnswer);
	}
}
