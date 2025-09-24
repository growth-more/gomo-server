package com.gomo.app.survey.application;

import java.util.List;
import java.util.UUID;

public record CreateSurveyResultCommand(UUID respondentId, List<SurveyItemDto> selectedSurveyItems) {

	public static CreateSurveyResultCommand of(UUID respondentId, List<SurveyItemDto> surveyResult) {
		return new CreateSurveyResultCommand(respondentId, surveyResult);
	}
}
