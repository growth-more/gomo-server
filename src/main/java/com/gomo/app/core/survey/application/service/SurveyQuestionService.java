package com.gomo.app.core.survey.application.service;

import java.util.List;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.survey.application.dto.SurveyItemDto;
import com.gomo.app.core.survey.application.dto.SurveyQuestionDto;
import com.gomo.app.core.survey.domain.repository.SurveyItemRepository;
import com.gomo.app.core.survey.domain.repository.SurveyQuestionRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class SurveyQuestionService {

	private final SurveyQuestionRepository surveyQuestionRepository;
	private final SurveyItemRepository surveyItemRepository;

	public List<SurveyQuestionDto> findAll() {
		return surveyQuestionRepository.findAll().stream()
			.map(question -> {
				List<SurveyItemDto> surveyItems = surveyItemRepository.findAllBySurveyQuestionId(question.getId()).stream()
					.map(SurveyItemDto::from)
					.toList();
				return SurveyQuestionDto.from(question, surveyItems);
			}).toList();
	}
}
