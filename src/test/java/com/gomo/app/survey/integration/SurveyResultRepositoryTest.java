package com.gomo.app.survey.integration;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gomo.app.common.IntegrationTestBase;
import com.gomo.app.survey.common.dataprovider.SurveyItemDataProvider;
import com.gomo.app.survey.common.dataprovider.SurveyQuestionDataProvider;
import com.gomo.app.survey.common.util.SurveyResultDataHelper;
import com.gomo.app.survey.domain.model.RespondentId;
import com.gomo.app.survey.domain.model.SurveyItem;
import com.gomo.app.survey.domain.model.SurveyQuestion;
import com.gomo.app.survey.domain.model.SurveyResult;
import com.gomo.app.survey.domain.repository.SurveyResultRepository;

@DisplayName("[Domain integration]: 설문 결과 DB 접근 테스트")
public class SurveyResultRepositoryTest extends IntegrationTestBase {

	@Autowired
	SurveyResultRepository sut;

	@Autowired
	SurveyQuestionDataProvider surveyQuestionDataProvider;
	SurveyQuestion surveyQuestion;

	@Autowired
	SurveyItemDataProvider surveyItemDataProvider;
	SurveyItem surveyItem;

	@Autowired
	SurveyResultDataHelper surveyResultDataHelper;

	@BeforeEach
	void setUp() {
		surveyQuestion = surveyQuestionDataProvider.surveyQuestion();
		surveyItem = surveyItemDataProvider.firstSurveyItem();
	}

	@AfterEach
	void tearDown() {
		surveyResultDataHelper.cleanUp();
	}

	@DisplayName("회원의 설문 결과를 등록한 후, 조회한다.")
	@Test
	void create_survey_result_by_member() {
		RespondentId respondentId = RespondentId.of(UUID.randomUUID());
		List<SurveyResult> surveyResults = List.of(createSurveyResult(respondentId), createSurveyResult(respondentId));

		sut.saveAll(surveyResults);

		List<SurveyResult> actual = sut.findAllByRespondentId(respondentId.getId());
		assertThat(actual).hasSize(2);
		assertThat(actual).usingRecursiveComparison().isEqualTo(surveyResults);
	}

	private SurveyResult createSurveyResult(RespondentId respondentId) {
		return SurveyResult.of(
			respondentId,
			surveyItem.getSurveyQuestionId(),
			surveyItem.getId(),
			surveyItem.getContent(),
			null
		);
	}
}
