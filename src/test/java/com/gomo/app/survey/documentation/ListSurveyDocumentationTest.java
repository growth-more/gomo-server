package com.gomo.app.survey.documentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.common.util.LoginMemberHelper;
import com.gomo.app.survey.common.dataprovider.SurveyItemDataProvider;
import com.gomo.app.survey.common.dataprovider.SurveyQuestionDataProvider;
import com.gomo.app.survey.common.util.SurveyResultDataHelper;
import com.gomo.app.survey.documentation.snippet.ListSurveySnippet;
import com.gomo.app.survey.domain.model.SurveyItem;
import com.gomo.app.survey.domain.model.SurveyQuestion;

@DisplayName("[Presentation documentation]: 설문 목록 조회 테스트")
public class ListSurveyDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = ListSurveySnippet.create();

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

	@DisplayName("사용자가 설문 목록을 조회한다.")
	@Test
	void list_survey() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + token)
			.when()
			.get("/surveys")
			.then()
			.statusCode(OK.value())
			.body("surveyQuestions", hasSize(1))
			.body("surveyQuestions.id", containsInAnyOrder(equalTo(surveyQuestion.getId().toString())))
			.body("surveyQuestions.questionSelectType", containsInAnyOrder(equalTo(surveyQuestion.getQuestionSelectType().name())))
			.body("surveyQuestions.required", containsInAnyOrder(equalTo(surveyQuestion.isRequired())))
			.body("surveyQuestions.content", containsInAnyOrder(equalTo(surveyQuestion.getContent())))
			.body("surveyQuestions[0].surveyItems", hasSize(2))
			.body("surveyQuestions[0].surveyItems.id", containsInAnyOrder(
				firstSurveyItem.getId().toString(),
				secondSurveyItem.getId().toString()
			))
			.body("surveyQuestions[0].surveyItems.content", containsInAnyOrder(
				firstSurveyItem.getContent(),
				secondSurveyItem.getContent()
			))
			.body("surveyQuestions[0].surveyItems.displayOrder", containsInAnyOrder(
				firstSurveyItem.getDisplayOrder().getDisplayOrder(),
				secondSurveyItem.getDisplayOrder().getDisplayOrder()
			));
	}
}
