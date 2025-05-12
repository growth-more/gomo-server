package com.gomo.app.member.documentation;

import static io.restassured.RestAssured.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.member.documentation.snippet.UpdateQuestPropertySnippet;
import com.gomo.app.member.presentation.request.UpdateQuestPropertyRequest;

@DisplayName("[Presentation Documentation]: 퀘스트 설정 값을 변경한다.")
public class UpdateQuestPropertyDocumentationTest extends DocumentationTestBase {

	private static final String UPDATE_PROPERTY_URL = "/members/properties/quests";

	private final RestDocumentationFilter filter = UpdateQuestPropertySnippet.create();
	private final RestDocumentationFilter errorFilter = UpdateQuestPropertySnippet.createError();

	@DisplayName("퀘스트 설정 값을 변경한다.")
	@Test
	void update_quest_property() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.body(UpdateQuestPropertyRequest.of(1, 1, 1))
			.when()
			.put(UPDATE_PROPERTY_URL)
			.then()
			.statusCode(NO_CONTENT.value());
	}
}
