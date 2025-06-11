package com.gomo.app.survey.documentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.survey.documentation.snippet.ListSurveySnippet;
import com.gomo.app.survey.domain.model.SurveyItem;
import com.gomo.app.survey.domain.model.SurveyQuestion;
import com.gomo.app.survey.domain.repository.SurveyItemRepository;
import com.gomo.app.survey.domain.repository.SurveyQuestionRepository;
import com.gomo.app.survey.fixture.SurveyItemFixture;
import com.gomo.app.survey.fixture.SurveyQuestionFixture;

@DisplayName("[Presentation documentation]: 설문 목록 조회 테스트")
public class ListSurveyDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = ListSurveySnippet.create();

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

	@DisplayName("사용자가 설문 목록을 조회한다.")
	@Test
	void list_survey() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.when()
			.get("/surveys")
			.then()
			.statusCode(OK.value())
			.body("surveyQuestions", hasSize(1))
			.body("surveyQuestions[0].surveyItems", hasSize(2));
	}
}
