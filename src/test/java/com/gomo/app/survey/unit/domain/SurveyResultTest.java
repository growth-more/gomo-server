package com.gomo.app.survey.unit.domain;

import static org.assertj.core.api.Assertions.*;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.gomo.app.core.survey.domain.model.RespondentId;
import com.gomo.app.core.survey.domain.model.SurveyItemId;
import com.gomo.app.core.survey.domain.model.SurveyQuestionId;
import com.gomo.app.core.survey.domain.model.SurveyResult;
import com.gomo.app.core.survey.exception.SurveyResultConstraintViolationException;
import com.gomo.app.core.survey.exception.SurveyResultErrorCode;

@DisplayName("[Domain unit]: 설문 결과 생성 테스트")
public class SurveyResultTest {

	private static final RespondentId RESPONDENT_ID = RespondentId.of(UUID.randomUUID());
	private static final SurveyQuestionId SURVEY_QUESTION_ID = SurveyQuestionId.of(UUID.randomUUID());
	private static final SurveyItemId SURVEY_ITEM_ID = SurveyItemId.of(UUID.randomUUID());
	private static final String NOT_OTHER_CONTENT = "survey item content";
	private static final String OTHER = "기타";

	@DisplayName("설문 결과를 생성한다.")
	@Test
	void create_survey_result() {
		SurveyResult surveyResult = SurveyResult.of(RESPONDENT_ID, SURVEY_QUESTION_ID, SURVEY_ITEM_ID, NOT_OTHER_CONTENT, null);

		assertThat(surveyResult)
			.extracting("respondentId", "surveyQuestionId", "surveyItemId", "surveyItemContent", "customAnswer")
			.containsExactly(RESPONDENT_ID, SURVEY_QUESTION_ID, SURVEY_ITEM_ID, NOT_OTHER_CONTENT, null);
	}

	@DisplayName("기타 항목을 선택하지 않았다면, 사용자가 직접 입력한 답변이 없어야 한다.")
	@Test
	void create_no_other_selected_survey_result_with_no_custom_answer() {
		SurveyResult surveyResult = SurveyResult.of(RESPONDENT_ID, SURVEY_QUESTION_ID, SURVEY_ITEM_ID, NOT_OTHER_CONTENT, null);

		assertThat(surveyResult.getCustomAnswer()).isNull();
	}

	@DisplayName("기타 항목을 선택했다면, 사용자가 직접 입력한 답변이 있어야 한다.")
	@Test
	void create_other_selected_survey_result_with_custom_answer() {
		SurveyResult surveyResult = SurveyResult.of(RESPONDENT_ID, SURVEY_QUESTION_ID, SURVEY_ITEM_ID, OTHER, "custom answer");

		assertThat(surveyResult.getCustomAnswer()).isNotBlank();
	}

	@DisplayName("기타 항목을 선택한 사용자가 직접 답변을 입력하지 않는다.")
	@Test
	void create_other_selected_survey_result_with_no_custom_answer() {
		assertThatThrownBy(() -> SurveyResult.of(RESPONDENT_ID, SURVEY_QUESTION_ID, SURVEY_ITEM_ID, OTHER, null))
			.isInstanceOf(SurveyResultConstraintViolationException.class)
			.hasMessageContaining(SurveyResultErrorCode.MISSING_CUSTOM_ANSWER.getMessage());
	}

	@DisplayName("사용자가 직접 입력한 답변이 최대 길이를 초과한다.")
	@Test
	void create_survey_result_with_too_long_custom_answer() {
		String longCustomAnswer = Stream.generate(() -> "a").limit(21).collect(Collectors.joining());

		assertThatThrownBy(() -> SurveyResult.of(RESPONDENT_ID, SURVEY_QUESTION_ID, SURVEY_ITEM_ID, OTHER, longCustomAnswer))
			.isInstanceOf(SurveyResultConstraintViolationException.class)
			.hasMessageContaining(SurveyResultErrorCode.TOO_LONG.getMessage());
	}

	@DisplayName("사용자가 직접 입력한 답변이 금지 문자를 포함한다.")
	@Test
	void create_survey_result_with_forbidden_custom_answer() {
		String forbiddenCustomAnswer = "[<>&';|{}[]()`]--*";

		assertThatThrownBy(() -> SurveyResult.of(RESPONDENT_ID, SURVEY_QUESTION_ID, SURVEY_ITEM_ID, OTHER, forbiddenCustomAnswer))
			.isInstanceOf(SurveyResultConstraintViolationException.class)
			.hasMessageContaining(SurveyResultErrorCode.FORBIDDEN.getMessage());
	}
}
