package com.gomo.app.survey.domain.repository;

import java.util.List;
import java.util.UUID;

import com.gomo.app.survey.domain.model.SurveyResult;

public interface SurveyResultRepository {

	void saveAll(List<SurveyResult> surveyResults);

	List<SurveyResult> findAllByRespondentId(UUID respondentId);
}
