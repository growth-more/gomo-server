package com.gomo.app.member.documentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.member.documentation.snippet.ReadQuestPropertySnippet;

@DisplayName("[Presentation Documentation]: 퀘스트 설정 값을 조회한다.")
public class ReadQuestPropertyDocumentationTest extends DocumentationTestBase {

	private static final String READ_PROPERTY_URL = "/members/properties/quests";

	private final RestDocumentationFilter filter = ReadQuestPropertySnippet.create();
	private final RestDocumentationFilter errorFilter = ReadQuestPropertySnippet.createError();

	@DisplayName("퀘스트 설정 값을 조회한다.")
	@Test
	void update_quest_property() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.when()
			.get(READ_PROPERTY_URL)
			.then()
			.statusCode(OK.value())
			.body("dailyThreshold", equalTo(0))
			.body("weeklyThreshold", equalTo(0))
			.body("monthlyThreshold", equalTo(0));
	}
}
