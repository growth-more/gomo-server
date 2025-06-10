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
import com.gomo.app.survey.domain.model.RespondentId;
import com.gomo.app.survey.domain.model.SurveyItem;
import com.gomo.app.survey.domain.model.SurveyQuestion;
import com.gomo.app.survey.domain.model.SurveyResult;
import com.gomo.app.survey.domain.repository.SurveyItemRepository;
import com.gomo.app.survey.domain.repository.SurveyQuestionRepository;
import com.gomo.app.survey.domain.repository.SurveyResultRepository;
import com.gomo.app.survey.fixture.SurveyItemFixture;
import com.gomo.app.survey.fixture.SurveyQuestionFixture;

@DisplayName("[Domain integration]: 설문 결과 DB 접근 테스트")
public class SurveyResultRepositoryTest extends IntegrationTestBase {

	@Autowired
	SurveyResultRepository sut;

	@Autowired
	private SurveyQuestionRepository surveyQuestionRepository;
	private SurveyQuestion surveyQuestion;

	@Autowired
	private SurveyItemRepository surveyItemRepository;
	private SurveyItem surveyItem1;
	private SurveyItem surveyItem2;

	@BeforeEach
	public void setUp() {
		surveyQuestion = SurveyQuestionFixture.surveyQuestion();
		surveyQuestionRepository.save(surveyQuestion);
		surveyItem1 = SurveyItemFixture.surveyItem(surveyQuestion.getId().getId(), "직업은?", 1);
		surveyItem2 = SurveyItemFixture.surveyItem(surveyQuestion.getId().getId(), "기타", 2);
		surveyItemRepository.saveAll(List.of(surveyItem1, surveyItem2));
	}

	@AfterEach
	void tearDown() {
		surveyQuestionRepository.deleteAllInBatch();
		surveyItemRepository.deleteAllInBatch();
	}

	@DisplayName("회원의 설문 결과를 등록한 후, 조회한다.")
	@Test
	void create_survey_result_by_member() {
		RespondentId respondentId = RespondentId.of(UUID.randomUUID());
		List<SurveyResult> surveyResults = List.of(createSurveyResult(respondentId, surveyItem1, null),
			createSurveyResult(respondentId, surveyItem2, "foo"));

		sut.saveAll(surveyResults);

		List<SurveyResult> actual = sut.findAllByRespondentId(respondentId.getId());
		assertThat(actual).hasSize(2);
		assertThat(actual).usingRecursiveComparison().isEqualTo(surveyResults);
	}

	private SurveyResult createSurveyResult(RespondentId respondentId, SurveyItem surveyItem, String customAnswer) {
		return SurveyResult.of(
			respondentId,
			surveyItem.getSurveyQuestionId(),
			surveyItem.getId(),
			surveyItem.getContent(),
			customAnswer
		);
	}
}
