package com.gomo.app.member.documentation;

import static com.gomo.app.common.exception.DomainErrorCode.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.member.documentation.snippet.CheckDuplicatedHandleSnippet;
import com.gomo.app.member.exception.MemberErrorCode;

@DisplayName("[Presentation documentation]: 핸들 중복 테스트")
public class CheckDuplicatedHandleDocumentationTest extends DocumentationTestBase {

	private static final String DUPLICATED_HANDLE_URL = "/members/handles/duplicate";
	private static final String SHORT_HANDLE = "@G";
	private static final String LONG_HANDLE = "@"+"a".repeat(31);

	private final RestDocumentationFilter filter = CheckDuplicatedHandleSnippet.create();
	private final RestDocumentationFilter errorFilter = CheckDuplicatedHandleSnippet.createError();

	@DisplayName("사용자가 핸들 중복을 확인한다.")
	@Test
	void check_duplicated_handle() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.param("handle", "@TEST")
			.when()
			.get(DUPLICATED_HANDLE_URL)
			.then()
			.statusCode(OK.value());
	}

	@DisplayName("사용자가 중복된 핸들로 핸들 중복 확인한다.")
	@Test
	void check_duplicated_handle_with_duplicated_handle() {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.param("handle", "@GOMOTEST")
			.when()
			.get(DUPLICATED_HANDLE_URL)
			.then()
			.statusCode(CONFLICT.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo(CONFLICT.value()))
			.body("code", equalTo(MemberErrorCode.HANDLE_DUPLICATED.name()))
			.body("message", equalTo("Handle already exists"))
			.body("path", equalTo(DUPLICATED_HANDLE_URL));
	}

	@DisplayName("사용자가 최소 길이보다 짧은 핸들로 핸들 중복을 확인하다.")
	@Test
	void check_duplicated_handle_with_short_handle() throws JsonProcessingException {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.param("handle", SHORT_HANDLE)
			.when()
			.get(DUPLICATED_HANDLE_URL)
			.then()
			.statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo(INVALID_PARAMETER.getHttpStatus()))
			.body("code", equalTo(INVALID_PARAMETER.name()))
			.body("message", equalTo("Handle must be at least 3 characters"))
			.body("path", equalTo(DUPLICATED_HANDLE_URL));
	}

	@DisplayName("사용자가 최대 길이보다 긴 핸들로 핸들 중복을 확인하다.")
	@Test
	void check_duplicated_handle_with_long_handle() throws JsonProcessingException {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.param("handle", LONG_HANDLE)
			.when()
			.get(DUPLICATED_HANDLE_URL)
			.then()
			.statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo(INVALID_PARAMETER.getHttpStatus()))
			.body("code", equalTo(INVALID_PARAMETER.name()))
			.body("message", equalTo("Handle must not exceed 30 characters"))
			.body("path", equalTo(DUPLICATED_HANDLE_URL));
	}
}
