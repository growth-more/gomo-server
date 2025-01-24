package com.gomo.app.survey.presentation.request;

import com.gomo.app.survey.domain.model.SurveyItemId;
import com.gomo.app.survey.domain.model.SurveyQuestionId;

import lombok.Getter;

@Getter
public class SelectedSurveyItem {

	private SurveyQuestionId surveyQuestionId;
	private SurveyItemId surveyItemId;

	private SelectedSurveyItem(
		SurveyQuestionId surveyQuestionId,
		SurveyItemId surveyItemId
	) {
		this.surveyQuestionId = surveyQuestionId;
		this.surveyItemId = surveyItemId;
	}

	public static SelectedSurveyItem of(
		SurveyQuestionId surveyQuestionId,
		SurveyItemId surveyItemId
	) {
		return new SelectedSurveyItem(surveyQuestionId, surveyItemId);
	}
}
