package com.gomo.app.core.survey.presentation.request;

import java.util.List;
import java.util.UUID;

import com.gomo.app.core.survey.application.CreateSurveyResultCommand;

import lombok.Getter;

@Getter
public class CreateSurveyResultRequest {

	private List<SelectedSurveyItemRequest> surveyResult;

	private CreateSurveyResultRequest(List<SelectedSurveyItemRequest> surveyResult) {
		this.surveyResult = surveyResult;
	}

	public static CreateSurveyResultRequest of(List<SelectedSurveyItemRequest> surveyResult) {
		return new CreateSurveyResultRequest(surveyResult);
	}

	public CreateSurveyResultCommand toCommand(UUID respondentId) {
		return CreateSurveyResultCommand.of(respondentId, surveyResult.stream().map(SelectedSurveyItemRequest::toSurveyItemDto).toList());
	}
}
