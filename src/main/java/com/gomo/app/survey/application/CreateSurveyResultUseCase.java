package com.gomo.app.survey.application;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.survey.domain.repository.SurveyResultRepository;
import com.gomo.app.survey.presentation.request.CreateSurveyResultRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class CreateSurveyResultUseCase {

    private final SurveyResultRepository surveyResultRepository;

    public void create(CreateSurveyResultRequest request) {
    }
}
