package com.gomo.app.core.survey.domain.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gomo.app.core.survey.domain.model.SurveyItem;

public interface SurveyItemRepository extends JpaRepository<SurveyItem, UUID> {

	List<SurveyItem> findAllBySurveyQuestionId(UUID surveyQuestionId);
}
