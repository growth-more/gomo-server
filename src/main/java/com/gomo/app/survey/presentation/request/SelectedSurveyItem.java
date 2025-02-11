package com.gomo.app.survey.presentation.request;

import java.util.UUID;

import lombok.Getter;

@Getter
public class SelectedSurveyItem {

	private UUID surveyQuestionId;
	private UUID surveyItemId;
	private String surveyItemContent;
	private String customAnswer;

	private SelectedSurveyItem(
		UUID surveyQuestionId,
		UUID surveyItemId,
		String surveyItemContent,
		String customAnswer
	) {
		this.surveyQuestionId = surveyQuestionId;
		this.surveyItemId = surveyItemId;
		this.surveyItemContent = surveyItemContent;
		this.customAnswer = customAnswer;
	}

	public static SelectedSurveyItem of(
		UUID surveyQuestionId,
		UUID surveyItemId,
		String surveyItemContent,
		String customAnswer
	) {
		return new SelectedSurveyItem(surveyQuestionId, surveyItemId, surveyItemContent, customAnswer);
	}
}
