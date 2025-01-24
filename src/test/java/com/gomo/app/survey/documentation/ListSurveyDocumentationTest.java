package com.gomo.app.survey.documentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
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
import com.gomo.app.survey.documentation.snippet.ListSurveySnippet;

public class ListSurveyDocumentationTest extends DocumentationTestBase {

	private static final String SURVEY_URL = "/surveys";

	private final RestDocumentationFilter filter = ListSurveySnippet.create();

	@Autowired
	private LoginMemberHelper loginHelper;

	@BeforeEach
	public void setUp() {
		sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
	}

	// TODO <jhl221123>: 개수 외 다른 필드도 검증 필요
	@DisplayName("사용자가 설문 목록을 조회한다.")
	@Test
	void list_survey() {
		given(this.specification).filter(filter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.when()
			.get(SURVEY_URL)
			.then()
			.statusCode(OK.value())
			.body("questions", hasSize(1))
			.body("questions[0].items", hasSize(2));
	}
}
