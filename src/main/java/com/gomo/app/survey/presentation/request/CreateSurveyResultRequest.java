package com.gomo.app.survey.presentation.request;

import java.util.List;

import lombok.Getter;

@Getter
public class CreateSurveyResultRequest {

	private List<SelectedSurveyItem> surveyResult;

	private CreateSurveyResultRequest(List<SelectedSurveyItem> surveyResult) {
		this.surveyResult = surveyResult;
	}

	public static CreateSurveyResultRequest of(List<SelectedSurveyItem> surveyResult) {
		return new CreateSurveyResultRequest(surveyResult);
	}
}
