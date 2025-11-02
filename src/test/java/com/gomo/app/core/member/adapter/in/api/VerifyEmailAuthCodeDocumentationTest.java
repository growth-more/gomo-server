package com.gomo.app.core.member.adapter.in.api;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.core.member.adapter.in.api.snippet.VerifyEmailAuthCodeSnippet;
import com.gomo.app.support.auth.application.port.in.AuthCodeIssuer;
import com.gomo.app.support.auth.domain.exception.AuthErrorCode;
import com.gomo.app.support.auth.domain.repository.AuthCodeRepository;
import com.gomo.app.test.DocumentationTestBase;

@DisplayName("[Presentation Documentation]: 이메일 인증 코드 테스트")
public class VerifyEmailAuthCodeDocumentationTest extends DocumentationTestBase {

	private static final String URL = "/members/emails/codes/verify";

	private final RestDocumentationFilter filter = VerifyEmailAuthCodeSnippet.create();
	private final RestDocumentationFilter errorFilter = VerifyEmailAuthCodeSnippet.createError();

	@Autowired
	AuthCodeIssuer authCodeIssuer;

	@Autowired
	AuthCodeRepository authCodeRepository;

	@AfterEach
	void teardown() {
		authCodeRepository.delete(sessionEmail);
	}

	@DisplayName("사용자가 올바른 인증코드를 이용하여 검증한다.")
	@Test
	void verify_email_auth_code_with_correct_code() {
		authCodeIssuer.sendToEmail(sessionEmail);
		String authCode = authCodeRepository.findByEmail(sessionEmail).get();
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.param("email", sessionEmail)
			.param("code", authCode)
			.when()
			.get(URL)
			.then()
			.statusCode(OK.value());
	}

	@DisplayName("사용자가 틀린 인증코드를 이용하여 검증한다.")
	@Test
	void verify_email_auth_code_with_incorrect_code() {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.param("email", sessionEmail)
			.param("code", "00000")
			.when()
			.get(URL)
			.then()
			.statusCode(AuthErrorCode.INVALID_AUTH_CODE.getHttpStatus())
			.body("timestamp", instanceOf(String.class))
			.body("path", equalTo(URL))
			.body("httpStatus", equalTo(AuthErrorCode.INVALID_AUTH_CODE.getHttpStatus()))
			.body("code", equalTo(AuthErrorCode.INVALID_AUTH_CODE.getErrorCode()))
			.body("message", equalTo(AuthErrorCode.INVALID_AUTH_CODE.getMessage()));
	}
}
