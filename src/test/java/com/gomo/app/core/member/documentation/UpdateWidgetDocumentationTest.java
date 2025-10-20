package com.gomo.app.core.member.documentation;

import static io.restassured.RestAssured.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.core.member.documentation.snippet.UpdateWidgetSnippet;
import com.gomo.app.core.member.presentation.request.UpdateWidgetRequest;
import com.gomo.app.test.DocumentationTestBase;

@DisplayName("[Presentation Documentation]: 핸들 수정 테스트")
class UpdateWidgetDocumentationTest extends DocumentationTestBase {

	private static final String URL = "/members/widgets";

	private final RestDocumentationFilter filter = UpdateWidgetSnippet.create();
	private final RestDocumentationFilter errorFilter = UpdateWidgetSnippet.createError();

	@DisplayName("위젯 스냅샷을 수정한다.")
	@Test
	void update_widget_snapshot() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.body(UpdateWidgetRequest.of("{ \"test\": \"test_snapshot\" }"))
			.when()
			.put(URL)
			.then()
			.statusCode(NO_CONTENT.value());
	}
}
