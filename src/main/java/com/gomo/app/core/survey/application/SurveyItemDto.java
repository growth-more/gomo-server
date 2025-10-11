package com.gomo.app.core.survey.application;

import java.util.UUID;

import com.gomo.app.core.survey.domain.model.SurveyItem;

public record SurveyItemDto(UUID id, UUID surveyQuestionId, String content, Integer displayOrder, String customAnswer) {

	public static SurveyItemDto withCustomAnswer(UUID id, UUID surveyQuestionId, String content, Integer displayOrder, String customAnswer) {
		return new SurveyItemDto(id, surveyQuestionId, content, displayOrder, customAnswer);
	}

	public static SurveyItemDto from(SurveyItem surveyItem) {
		return new SurveyItemDto(surveyItem.getId(), surveyItem.getSurveyQuestionId(), surveyItem.getContent(), surveyItem.getDisplayOrder().getDisplayOrder(), null);
	}
}
