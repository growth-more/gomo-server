package com.gomo.app.core.survey.application;

import java.util.List;
import java.util.UUID;

import com.gomo.app.core.survey.domain.model.SurveyQuestion;

public record SurveyQuestionDto(UUID id, String questionSelectType, boolean isRequired, String content, List<SurveyItemDto> surveyItems) {

	public static SurveyQuestionDto of(UUID id, String questionSelectType, boolean isRequired, String content, List<SurveyItemDto> surveyItems) {
		return new SurveyQuestionDto(id, questionSelectType, isRequired, content, surveyItems);
	}

	public static SurveyQuestionDto from(SurveyQuestion question, List<SurveyItemDto> surveyItems) {
		return new SurveyQuestionDto(question.id(), question.getQuestionSelectType().name(), question.isRequired(), question.getContent(), surveyItems);
	}
}
