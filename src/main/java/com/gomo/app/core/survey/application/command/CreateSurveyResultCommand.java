package com.gomo.app.core.survey.application.command;

import java.util.List;
import java.util.UUID;

import com.gomo.app.core.survey.application.dto.SurveyItemDto;

public record CreateSurveyResultCommand(UUID respondentId, List<SurveyItemDto> selectedSurveyItems) {

	public static CreateSurveyResultCommand of(UUID respondentId, List<SurveyItemDto> surveyResult) {
		return new CreateSurveyResultCommand(respondentId, surveyResult);
	}
}
