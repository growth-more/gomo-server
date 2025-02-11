package com.gomo.app.survey.common.dataprovider;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.gomo.app.survey.domain.model.SurveyItem;
import com.gomo.app.survey.domain.model.SurveyItemId;
import com.gomo.app.survey.domain.repository.SurveyItemRepository;

import jakarta.annotation.PostConstruct;

/**
 * 실제 데이터베이스에 존재하는 설문 데이터를 제공한다.
 * @ 총 두 가지 데이터가 존재한다.
 * @ 1. survey item1 - 학생
 * @ 2. survey item2 - 기타
 */
@Component
public class SurveyItemDataProvider {

	private static final String FIRST_SURVEY_ITEM_ID = "a115056c-d874-11ef-8a6a-454f4733cb7c";
	private static final String SECOND_SURVEY_ITEM_ID = "b94a39ed-d874-11ef-b96f-c12f103e5c2c";
	private SurveyItem firstSurveyItem;
	private SurveyItem secondSurveyItem;

	@Autowired
	private SurveyItemRepository surveyItemRepository;

	@PostConstruct
	public void initialize() {
		firstSurveyItem = surveyItemRepository.findById(SurveyItemId.of(UUID.fromString(FIRST_SURVEY_ITEM_ID)))
			.orElseThrow(() -> new IllegalStateException("SurveyDataProvider 초기화 실패: FIRST_SURVEY_ITEM_ID에 해당하는 SurveyItem이 없습니다."));

		secondSurveyItem = surveyItemRepository.findById(SurveyItemId.of(UUID.fromString(SECOND_SURVEY_ITEM_ID)))
			.orElseThrow(() -> new IllegalStateException("SurveyDataProvider 초기화 실패: SECOND_SURVEY_ITEM_ID에 해당하는 SurveyItem이 없습니다."));
	}

	public SurveyItem firstSurveyItem() {
		return this.firstSurveyItem;
	}

	public SurveyItem secondSurveyItem() {
		return this.secondSurveyItem;
	}
}
