package com.gomo.app.survey.documentation;

import static io.restassured.RestAssured.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.common.util.LoginMemberHelper;
import com.gomo.app.survey.common.dataprovider.SurveyItemDataProvider;
import com.gomo.app.survey.common.dataprovider.SurveyQuestionDataProvider;
import com.gomo.app.survey.common.util.SurveyResultDataHelper;
import com.gomo.app.survey.documentation.snippet.CreateSurveyAnswerSnippet;
import com.gomo.app.survey.domain.model.SurveyItem;
import com.gomo.app.survey.domain.model.SurveyQuestion;
import com.gomo.app.survey.presentation.request.CreateSurveyResultRequest;
import com.gomo.app.survey.presentation.request.SelectedSurveyItem;

@DisplayName("[Presentation documentation]: 설문 결과 생성 테스트")
public class CreateSurveyAnswerDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = CreateSurveyAnswerSnippet.create();

	@Autowired
	private SurveyQuestionDataProvider surveyQuestionDataProvider;
	private SurveyQuestion surveyQuestion;

	@Autowired
	private SurveyItemDataProvider surveyItemDataProvider;
	private SurveyItem firstSurveyItem;
	private SurveyItem secondSurveyItem;

	@Autowired
	private SurveyResultDataHelper surveyResultDataHelper;

	@BeforeEach
	public void setUp() {
		surveyQuestion = surveyQuestionDataProvider.surveyQuestion();
		firstSurveyItem = surveyItemDataProvider.firstSurveyItem();
		secondSurveyItem = surveyItemDataProvider.secondSurveyItem();
	}

	@AfterEach
	public void tearDown() {
		surveyResultDataHelper.cleanUp();
	}

	@DisplayName("사용자가 설문 답변을 등록한다.")
	@Test
	void create_survey_answer() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + token)
			.body(createRequest())
			.when()
			.post("/surveys")
			.then()
			.statusCode(CREATED.value());
	}

	private @NotNull CreateSurveyResultRequest createRequest() {
		return CreateSurveyResultRequest.of(
			List.of(
				SelectedSurveyItem.of(
					surveyQuestion.getId().getId(),
					firstSurveyItem.getId().getId(),
					firstSurveyItem.getContent(),
					null
				), SelectedSurveyItem.of(
					surveyQuestion.getId().getId(),
					secondSurveyItem.getId().getId(),
					secondSurveyItem.getContent(),
					"custom answer"
				)
			)
		);
	}
}
