package com.gomo.app.survey.documentation;

import static io.restassured.RestAssured.*;
import static org.springframework.http.HttpStatus.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.common.fixture.TestMemberFixture;
import com.gomo.app.common.util.LoginMemberHelper;
import com.gomo.app.survey.documentation.snippet.CreateSurveyAnswerSnippet;

public class CreateSurveyAnswerDocumentationTest extends DocumentationTestBase {

	private static final String SURVEY_URL = "/surveys";

	private final RestDocumentationFilter filter = CreateSurveyAnswerSnippet.create();

	@Autowired
	private LoginMemberHelper loginHelper;

	@BeforeEach
	public void setUp() {
		sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
	}

	@DisplayName("사용자가 설문 답변을 등록한다.")
	@Test
	void create_survey_answer() {
		given(this.specification).filter(filter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.when()
			.post(SURVEY_URL)
			.then()
			.statusCode(CREATED.value());
	}
}
