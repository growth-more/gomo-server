package com.gomo.app.member.documentation;

import static io.restassured.RestAssured.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.member.documentation.snippet.UpdatePasswordSnippet;
import com.gomo.app.core.member.presentation.request.UpdatePasswordRequest;

@DisplayName("[Presentation Documentation]: 비밀번호 수정 테스트")
public class UpdatePasswordDocumentationTest extends DocumentationTestBase {

	private static final String UPDATE_PASSWORD_URL = "/members/passwords";

	private final RestDocumentationFilter filter = UpdatePasswordSnippet.create();
	private final RestDocumentationFilter errorFilter = UpdatePasswordSnippet.createError();

	@DisplayName("비밀번호를 수정한다.")
	@Test
	void update_password() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.body(UpdatePasswordRequest.of("Test1234@", "Test1234!"))
			.when()
			.put(UPDATE_PASSWORD_URL)
			.then()
			.statusCode(NO_CONTENT.value());
	}
}
