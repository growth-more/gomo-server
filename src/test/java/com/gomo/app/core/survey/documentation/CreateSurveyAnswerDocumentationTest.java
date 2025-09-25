package com.gomo.app.core.survey.documentation;

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
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.core.survey.documentation.snippet.CreateSurveyAnswerSnippet;
import com.gomo.app.core.survey.domain.model.SurveyItem;
import com.gomo.app.core.survey.domain.model.SurveyQuestion;
import com.gomo.app.core.survey.domain.repository.SurveyItemRepository;
import com.gomo.app.core.survey.domain.repository.SurveyQuestionRepository;
import com.gomo.app.core.survey.fixture.SurveyItemFixture;
import com.gomo.app.core.survey.fixture.SurveyQuestionFixture;
import com.gomo.app.core.survey.presentation.request.CreateSurveyResultRequest;
import com.gomo.app.core.survey.presentation.request.SelectedSurveyItemRequest;

@DisplayName("[Presentation documentation]: 설문 결과 생성 테스트")
public class CreateSurveyAnswerDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = CreateSurveyAnswerSnippet.create();

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

	@DisplayName("사용자가 설문 답변을 등록한다.")
	@Test
	void create_survey_answer() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.body(createRequest())
			.when()
			.post("/surveys")
			.then()
			.statusCode(CREATED.value());
	}

	private @NotNull CreateSurveyResultRequest createRequest() {
		return CreateSurveyResultRequest.of(
			List.of(
				SelectedSurveyItemRequest.of(
					surveyQuestion.getId().getId(),
					surveyItem1.getId().getId(),
					surveyItem1.getContent(),
					null
				), SelectedSurveyItemRequest.of(
					surveyQuestion.getId().getId(),
					surveyItem2.getId().getId(),
					surveyItem2.getContent(),
					"custom answer"
				)
			)
		);
	}
}
