package com.gomo.app.survey.common.dataprovider;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gomo.app.survey.domain.model.SurveyQuestion;
import com.gomo.app.survey.domain.model.SurveyQuestionId;
import com.gomo.app.survey.domain.repository.SurveyQuestionRepository;

import jakarta.annotation.PostConstruct;

/**
 * 실제 데이터베이스에 존재하는 설문 데이터를 제공한다.
 */
@Component
public class SurveyQuestionDataProvider {

	private static final String SURVEY_QUESTION_ID = "3030e03f-d874-11ef-8c53-0f7e2e2f64c0";
	private SurveyQuestion surveyQuestion;

	@Autowired
	private SurveyQuestionRepository surveyQuestionRepository;

	@PostConstruct
	public void initialize() {
		surveyQuestion = surveyQuestionRepository.findById(SurveyQuestionId.of(UUID.fromString(SURVEY_QUESTION_ID)))
			.orElseThrow(() -> new IllegalStateException("SurveyDataProvider 초기화 실패: SURVEY_QUESTION_ID에 해당하는 SurveyQuestion이 없습니다."));
	}

	public SurveyQuestion surveyQuestion() {
		return this.surveyQuestion;
	}
}
