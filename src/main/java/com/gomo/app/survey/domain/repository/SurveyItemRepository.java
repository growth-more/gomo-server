package com.gomo.app.survey.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gomo.app.survey.domain.model.SurveyItem;
import com.gomo.app.survey.domain.model.SurveyItemId;

public interface SurveyItemRepository extends JpaRepository<SurveyItem, SurveyItemId> {
}
