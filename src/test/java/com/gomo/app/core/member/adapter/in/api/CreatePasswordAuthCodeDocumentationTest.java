package com.gomo.app.core.member.adapter.in.api;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.core.member.adapter.in.api.request.CreateEmailCodeRequest;
import com.gomo.app.core.member.adapter.in.api.snippet.CreatePasswordAuthCodeSnippet;
import com.gomo.app.core.member.domain.exception.code.MemberErrorCode;
import com.gomo.app.test.DocumentationTestBase;

@DisplayName("[Presentation Documentation]: 이메일 인증 코드 테스트")
public class CreatePasswordAuthCodeDocumentationTest extends DocumentationTestBase {

	private static final String URL = "/members/emails/codes/passwords/reset";

	private final RestDocumentationFilter filter = CreatePasswordAuthCodeSnippet.create();
	private final RestDocumentationFilter errorFilter = CreatePasswordAuthCodeSnippet.createError();

	@DisplayName("비밀번호 초기화를 위해 이메일 인증 코드 생성을 요청한다.")
	@Test
	void create_auth_code() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.body(CreateEmailCodeRequest.of(super.sessionEmail))
			.when()
			.post(URL)
			.then()
			.statusCode(CREATED.value());
	}

	@DisplayName("존재하지 않는 이메일로 인증 코드를 요청한다.")
	@Test
	void create_auth_code_with_nonexistent_email() {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.body(CreateEmailCodeRequest.of("nonexistent@naver.com"))
			.when()
			.post(URL)
			.then()
			.statusCode(MemberErrorCode.NOT_FOUND.getHttpStatus())
			.body("timestamp", instanceOf(String.class))
			.body("path", equalTo(URL))
			.body("httpStatus", equalTo(MemberErrorCode.NOT_FOUND.getHttpStatus()))
			.body("code", equalTo(MemberErrorCode.NOT_FOUND.getErrorCode()))
			.body("message", equalTo(MemberErrorCode.NOT_FOUND.getMessage()));
	}
}
