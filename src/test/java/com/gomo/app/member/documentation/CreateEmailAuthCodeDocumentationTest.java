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
import com.gomo.app.member.documentation.snippet.CreateEmailAuthCodeSnippet;
import com.gomo.app.member.exception.code.EmailErrorCode;
import com.gomo.app.member.presentation.request.CreateEmailAuthCodeRequest;

@DisplayName("[Presentation Documentation]: 이메일 인증 코드 테스트")
public class CreateEmailAuthCodeDocumentationTest extends DocumentationTestBase {

	private static final String EMAIL_AUTH_URL = "/members/emails/codes/auth";

	private final RestDocumentationFilter filter = CreateEmailAuthCodeSnippet.create();
	private final RestDocumentationFilter errorFilter = CreateEmailAuthCodeSnippet.createError();

	@DisplayName("이메일 인증 코드 생성을 요청한다.")
	@Test
	void create_auth_code() {
		given(this.specification).filter(filter)
			.log().all()
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.body(CreateEmailAuthCodeRequest.of("test@test.com"))
			.when()
			.post(EMAIL_AUTH_URL)
			.then()
			.statusCode(CREATED.value());
	}

	@DisplayName("중복된 이메일로 인증한다")
	@Test
	void create_auth_code_with_duplicated_email() {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.body(CreateEmailAuthCodeRequest.of("gomotest@naver.com"))
			.when()
			.post(EMAIL_AUTH_URL)
			.then()
			.statusCode(EmailErrorCode.DUPLICATED.getHttpStatus())
			.body("timestamp", instanceOf(String.class))
			.body("path", equalTo(EMAIL_AUTH_URL))
			.body("httpStatus", equalTo(EmailErrorCode.DUPLICATED.getHttpStatus()))
			.body("code", equalTo(EmailErrorCode.DUPLICATED.getErrorCode()))
			.body("message", equalTo(EmailErrorCode.DUPLICATED.getMessage()));
	}
}
