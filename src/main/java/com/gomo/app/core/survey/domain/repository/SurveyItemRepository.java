package com.gomo.app.core.survey.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gomo.app.core.survey.domain.model.SurveyItem;
import com.gomo.app.core.survey.domain.model.SurveyItemId;
import com.gomo.app.core.survey.domain.model.SurveyQuestionId;

public interface SurveyItemRepository extends JpaRepository<SurveyItem, SurveyItemId> {

	List<SurveyItem> findAllBySurveyQuestionId(SurveyQuestionId surveyQuestionId);
}
