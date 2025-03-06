package com.gomo.app.member.documentation;

import static com.gomo.app.common.exception.DomainErrorCode.INVALID_PARAMETER;
import static com.gomo.app.member.exception.MemberErrorCode.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.member.documentation.snippet.CreateEmailAuthCodeSnippet;
import com.gomo.app.member.presentation.request.CreateEmailAuthCodeRequest;

@DisplayName("[Presentation documentation]: 이메일 인증 코드 테스트")
public class CreateEmailAuthCodeDocumentationTest extends DocumentationTestBase {

	private static final String EMAIL_AUTH_URL = "/members/emails/codes/auth";
	private static final String INVALID_EMAIL = "invalid-email";

	private final RestDocumentationFilter filter = CreateEmailAuthCodeSnippet.create();
	private final RestDocumentationFilter errorFilter = CreateEmailAuthCodeSnippet.createError();

	@DisplayName("사용자가 이메일 인증 코드 생성을 요청한다.")
	@Test
	void validate_email() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.body(CreateEmailAuthCodeRequest.of("test@test.com"))
			.when()
			.post(EMAIL_AUTH_URL)
			.then()
			.statusCode(CREATED.value())
			.body("email", instanceOf(String.class));
	}

	@DisplayName("사용자가 중복된 이메일로 인증한다.")
	@Test
	void validate_email_with_duplicated_email() {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.body(CreateEmailAuthCodeRequest.of("gomotest@naver.com"))
			.when()
			.post(EMAIL_AUTH_URL)
			.then()
			.statusCode(CONFLICT.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo(CONFLICT.value()))
			.body("code", equalTo(EMAIL_DUPLICATED.name()))
			.body("message", equalTo("email already exists"))
			.body("path", equalTo(EMAIL_AUTH_URL));
	}

	@DisplayName("사용자가 잘못된 이메일 형식으로 인증한다.")
	@Test
	void validate_email_with_invalid_email() {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.body(CreateEmailAuthCodeRequest.of(INVALID_EMAIL))
			.when()
			.post(EMAIL_AUTH_URL)
			.then()
			.statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo(INVALID_PARAMETER.getHttpStatus()))
			.body("code", equalTo(INVALID_PARAMETER.name()))
			.body("message", equalTo("Email should be invalid format"))
			.body("path", equalTo(EMAIL_AUTH_URL));
	}
}
