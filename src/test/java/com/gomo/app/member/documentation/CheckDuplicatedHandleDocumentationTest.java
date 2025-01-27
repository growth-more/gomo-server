package com.gomo.app.member.documentation;

import static com.gomo.app.common.exception.DomainErrorCode.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.common.fixture.TestMemberFixture;
import com.gomo.app.member.common.constant.NonExistMemberField;
import com.gomo.app.member.documentation.snippet.CheckDuplicatedHandleSnippet;
import com.gomo.app.member.exception.MemberErrorCode;

/**
 * todo <jhl221123>: 핸들 변경 방식 결정 후, 삭제 필요
 * 중복 확인 후 삭제 or 변경과 함께 중복 확인
 */
public class CheckDuplicatedHandleDocumentationTest extends DocumentationTestBase {

	private static final String DUPLICATED_HANDLE_URL = "/members/handles/duplicate";
	private static final String SHORT_HANDLE = "@GO";

	private final RestDocumentationFilter filter = CheckDuplicatedHandleSnippet.create();
	private final RestDocumentationFilter errorFilter = CheckDuplicatedHandleSnippet.createError();

	@DisplayName("사용자가 핸들 중복을 확인한다.")
	@Test
	void check_duplicated_handle() {
		given(this.specification).filter(filter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.param("handle", NonExistMemberField.HANDLE)
			.when()
			.get(DUPLICATED_HANDLE_URL)
			.then()
			.statusCode(OK.value())
			.body("id", hasLength(36));
	}

	@DisplayName("사용자가 중복된 핸들로 핸들 중복 확인한다.")
	@Test
	void check_duplicated_handle_with_duplicated_handle() {
		given(this.specification).filter(errorFilter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.param("handle", TestMemberFixture.handle())
			.when()
			.get(DUPLICATED_HANDLE_URL)
			.then()
			.statusCode(CONFLICT.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo("409"))
			.body("code", equalTo(MemberErrorCode.HANDLE_DUPLICATED.name()))
			.body("message", equalTo("Handle already exists: " + TestMemberFixture.handle()))
			.body("path", equalTo(DUPLICATED_HANDLE_URL));
	}

	@DisplayName("사용자가 최소 길이보다 짧은 핸들로 핸들 중복을 확인하다.")
	@Test
	void check_duplicated_handle_with_short_handle() throws JsonProcessingException {
		given(this.specification).filter(errorFilter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.param("handle", SHORT_HANDLE)
			.when()
			.get(DUPLICATED_HANDLE_URL)
			.then()
			.statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo(INVALID_PARAMETER.getHttpStatus()))
			.body("code", equalTo(INVALID_PARAMETER.name()))
			.body("message", equalTo("Invalid parameter: " + SHORT_HANDLE))
			.body("path", equalTo(DUPLICATED_HANDLE_URL));
	}
}
