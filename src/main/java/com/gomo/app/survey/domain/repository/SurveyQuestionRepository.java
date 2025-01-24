package com.gomo.app.survey.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gomo.app.survey.domain.model.SurveyQuestion;
import com.gomo.app.survey.domain.model.SurveyQuestionId;

public interface SurveyQuestionRepository extends JpaRepository<SurveyQuestion, SurveyQuestionId> {
}
