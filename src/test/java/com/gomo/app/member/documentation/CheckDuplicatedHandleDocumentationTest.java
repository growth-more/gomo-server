package com.gomo.app.member.documentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.member.documentation.snippet.CheckDuplicatedHandleSnippet;
import com.gomo.app.member.exception.code.HandleErrorCode;

@DisplayName("[Presentation documentation]: 핸들 중복 테스트")
public class CheckDuplicatedHandleDocumentationTest extends DocumentationTestBase {

	private static final String URL = "/members/handles/duplicate";

	private final RestDocumentationFilter filter = CheckDuplicatedHandleSnippet.create();
	private final RestDocumentationFilter errorFilter = CheckDuplicatedHandleSnippet.createError();

	@DisplayName("핸들 중복을 확인한다.")
	@Test
	void check_duplicated_handle() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.param("handle", "@TEST")
			.when()
			.get(URL)
			.then()
			.statusCode(OK.value());
	}

	@DisplayName("중복된 핸들로 핸들 중복 확인한다.")
	@Test
	void check_duplicated_handle_with_duplicated_handle() {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.param("handle", "@GOMOTEST")
			.when()
			.get(URL)
			.then()
			.statusCode(HandleErrorCode.DUPLICATED.getHttpStatus())
			.body("timestamp", instanceOf(String.class))
			.body("path", equalTo(URL))
			.body("httpStatus", equalTo(HandleErrorCode.DUPLICATED.getHttpStatus()))
			.body("code", equalTo(HandleErrorCode.DUPLICATED.getErrorCode()))
			.body("message", equalTo(HandleErrorCode.DUPLICATED.getMessage()));
	}

	@DisplayName("최소 길이보다 짧은 핸들로 핸들 중복을 확인하다.")
	@Test
	void check_duplicated_handle_with_short_handle() throws JsonProcessingException {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.param("handle", "@G")
			.when()
			.get(URL)
			.then()
			.statusCode(HandleErrorCode.TOO_SHORT.getHttpStatus())
			.body("timestamp", instanceOf(String.class))
			.body("path", equalTo(URL))
			.body("httpStatus", equalTo(HandleErrorCode.TOO_SHORT.getHttpStatus()))
			.body("code", equalTo(HandleErrorCode.TOO_SHORT.getErrorCode()))
			.body("message", equalTo(HandleErrorCode.TOO_SHORT.getMessage()));
	}

	@DisplayName("최대 길이보다 긴 핸들로 핸들 중복을 확인하다.")
	@Test
	void check_duplicated_handle_with_long_handle() throws JsonProcessingException {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.param("handle", "@"+"a".repeat(31))
			.when()
			.get(URL)
			.then()
			.statusCode(HandleErrorCode.TOO_LONG.getHttpStatus())
			.body("timestamp", instanceOf(String.class))
			.body("path", equalTo(URL))
			.body("httpStatus", equalTo(HandleErrorCode.TOO_LONG.getHttpStatus()))
			.body("code", equalTo(HandleErrorCode.TOO_LONG.getErrorCode()))
			.body("message", equalTo(HandleErrorCode.TOO_LONG.getMessage()));
	}
}
