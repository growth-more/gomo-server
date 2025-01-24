package com.gomo.app.survey.presentation.request;

import java.util.List;

import com.gomo.app.survey.domain.model.RespondentId;

import lombok.Getter;

@Getter
public class CreateSurveyResultRequest {

	private RespondentId respondentId;
	private List<SelectedSurveyItem> surveyResult;

	private CreateSurveyResultRequest(
		RespondentId respondentId,
		List<SelectedSurveyItem> surveyResult
	) {
		this.respondentId = respondentId;
		this.surveyResult = surveyResult;
	}

	public static CreateSurveyResultRequest of(
		RespondentId respondentId,
		List<SelectedSurveyItem> surveyResult
	) {
		return new CreateSurveyResultRequest(respondentId, surveyResult);
	}
}
