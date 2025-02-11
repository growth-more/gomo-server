package com.gomo.app.survey.application;

import java.util.List;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.survey.domain.model.SurveyItem;
import com.gomo.app.survey.domain.model.SurveyQuestion;
import com.gomo.app.survey.domain.repository.SurveyItemRepository;
import com.gomo.app.survey.domain.repository.SurveyQuestionRepository;
import com.gomo.app.survey.presentation.response.ListSurveyQuestionResponse;
import com.gomo.app.survey.presentation.response.ReadSurveyItemResponse;
import com.gomo.app.survey.presentation.response.ReadSurveyQuestionResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadSurveyQuestionUseCase {

	private final SurveyQuestionRepository surveyQuestionRepository;
	private final SurveyItemRepository surveyItemRepository;

	public ListSurveyQuestionResponse findAll() {
		List<SurveyQuestion> questions = surveyQuestionRepository.findAll();

		List<ReadSurveyQuestionResponse> questionResponses = questions.stream()
			.map(question -> {
				List<SurveyItem> items = surveyItemRepository.findAllBySurveyQuestionId(question.getId());

				List<ReadSurveyItemResponse> itemResponses = items.stream()
					.map(ReadSurveyItemResponse::of)
					.toList();

				return ReadSurveyQuestionResponse.of(question, itemResponses);
			})
			.toList();

		return ListSurveyQuestionResponse.of(questionResponses);
	}
}
