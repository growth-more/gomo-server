package com.gomo.app.core.survey.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gomo.app.core.survey.domain.model.SurveyQuestion;
import com.gomo.app.core.survey.domain.model.SurveyQuestionId;

public interface SurveyQuestionRepository extends JpaRepository<SurveyQuestion, SurveyQuestionId> {
}
