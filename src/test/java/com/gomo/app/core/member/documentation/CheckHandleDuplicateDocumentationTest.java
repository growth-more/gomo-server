package com.gomo.app.core.member.documentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.core.member.documentation.snippet.CheckHandleDuplicateSnippet;
import com.gomo.app.core.member.exception.code.HandleErrorCode;

@DisplayName("[Presentation Documentation]: 핸들 중복 테스트")
public class CheckHandleDuplicateDocumentationTest extends DocumentationTestBase {

	private static final String CHECK_DUPLICATE_HANDLE_URL = "/members/handles/duplicate";

	private final RestDocumentationFilter filter = CheckHandleDuplicateSnippet.create();
	private final RestDocumentationFilter errorFilter = CheckHandleDuplicateSnippet.createError();

	@DisplayName("사용자가 핸들을 검증에 성공한다.(중복X)")
	@Test
	void check_duplicate_handle_success() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.param("handle", "@newhandle")
			.when()
			.get(CHECK_DUPLICATE_HANDLE_URL)
			.then()
			.statusCode(OK.value());
	}

	@DisplayName("사용자가 핸들을 검증에 실패한다.(중복)")
	@Test
	void check_duplicate_handle_failure() {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.param("handle", super.sessionHandle)
			.when()
			.get(CHECK_DUPLICATE_HANDLE_URL)
			.then()
			.statusCode(HandleErrorCode.DUPLICATED.getHttpStatus())
			.body("timestamp", instanceOf(String.class))
			.body("path", equalTo(CHECK_DUPLICATE_HANDLE_URL))
			.body("httpStatus", equalTo(HandleErrorCode.DUPLICATED.getHttpStatus()))
			.body("code", equalTo(HandleErrorCode.DUPLICATED.getErrorCode()))
			.body("message", equalTo(HandleErrorCode.DUPLICATED.getMessage()));
	}

}
