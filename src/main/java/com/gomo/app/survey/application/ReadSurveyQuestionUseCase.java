package com.gomo.app.survey.application;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.survey.domain.repository.SurveyQuestionRepository;
import com.gomo.app.survey.presentation.response.ListSurveyQuestionResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadSurveyQuestionUseCase {

	private final SurveyQuestionRepository surveyQuestionRepository;

	public ListSurveyQuestionResponse findAll() {
		return null;
	}
}
