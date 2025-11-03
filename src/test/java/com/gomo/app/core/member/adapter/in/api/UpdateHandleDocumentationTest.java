package com.gomo.app.core.member.adapter.in.api;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.core.member.adapter.in.api.request.UpdateHandleRequest;
import com.gomo.app.core.member.adapter.in.api.snippet.UpdateHandleSnippet;
import com.gomo.app.core.member.domain.exception.code.HandleErrorCode;
import com.gomo.app.test.DocumentationTestBase;

@DisplayName("[Presentation Documentation]: 핸들 수정 테스트")
public class UpdateHandleDocumentationTest extends DocumentationTestBase {

	private static final String UPDATE_HANDLE_URL = "/members/handles";

	private final RestDocumentationFilter filter = UpdateHandleSnippet.create();
	private final RestDocumentationFilter errorFilter = UpdateHandleSnippet.createError();

	@DisplayName("핸들을 수정한다.")
	@Test
	void update_handle() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.body(UpdateHandleRequest.of("@updateHandle"))
			.when()
			.put(UPDATE_HANDLE_URL)
			.then()
			.statusCode(NO_CONTENT.value());
	}

	@DisplayName("중복된 핸들로 인증한다")
	@Test
	void update_handle_with_duplicate_handle() {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.body(UpdateHandleRequest.of("@Test"))
			.when()
			.put(UPDATE_HANDLE_URL)
			.then()
			.statusCode(HandleErrorCode.DUPLICATED.getHttpStatus())
			.body("timestamp", instanceOf(String.class))
			.body("path", equalTo(UPDATE_HANDLE_URL))
			.body("httpStatus", equalTo(HandleErrorCode.DUPLICATED.getHttpStatus()))
			.body("code", equalTo(HandleErrorCode.DUPLICATED.getErrorCode()))
			.body("message", equalTo(HandleErrorCode.DUPLICATED.getMessage()));
	}
}
