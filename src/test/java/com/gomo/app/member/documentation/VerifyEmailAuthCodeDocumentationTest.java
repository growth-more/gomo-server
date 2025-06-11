package com.gomo.app.member.documentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.member.documentation.snippet.VerifyEmailAuthCodeSnippet;
import com.gomo.app.member.domain.repository.EmailAuthCodeRepository;
import com.gomo.app.member.exception.code.MemberErrorCode;
import com.gomo.app.member.presentation.EmailAuthCodeApi;
import com.gomo.app.member.presentation.request.CreateEmailAuthCodeRequest;

@DisplayName("[Presentation Documentation]: 이메일 인증 코드 테스트")
public class VerifyEmailAuthCodeDocumentationTest extends DocumentationTestBase {

	private static final String EMAIL_VERIFY_URL = "/members/emails/codes/auth";

	private final RestDocumentationFilter filter = VerifyEmailAuthCodeSnippet.create();
	private final RestDocumentationFilter errorFilter = VerifyEmailAuthCodeSnippet.createError();

	private static final String EMAIL = "test@test.com";

	@Autowired
	EmailAuthCodeApi emailAuthCodeApi;

	@Autowired
	EmailAuthCodeRepository emailAuthCodeRepository;

	private String AUTHCODE = "";

	@BeforeEach
	void setup() {
		createAuthCode(EMAIL);
		AUTHCODE = emailAuthCodeRepository.findByEmail(EMAIL).get();
	}

	@AfterEach
	void teardown() {
		emailAuthCodeRepository.delete(EMAIL);
	}

	@DisplayName("사용자가 올바른 인증코드를 이용하여 검증한다.")
	@Test
	void verify_email_auth_code_with_correct_code() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.param("email", EMAIL)
			.param("code", AUTHCODE)
			.when()
			.get(EMAIL_VERIFY_URL)
			.then()
			.statusCode(OK.value());
	}

	@DisplayName("사용자가 틀린 인증코드를 이용하여 검증한다.")
	@Test
	void verify_email_auth_code_with_incorrect_code() {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.param("email", EMAIL)
			.param("code", "00000")
			.when()
			.get(EMAIL_VERIFY_URL)
			.then()
			.statusCode(MemberErrorCode.AUTHENTICATION_FAILED.getHttpStatus())
			.body("timestamp", instanceOf(String.class))
			.body("path", equalTo(EMAIL_VERIFY_URL))
			.body("httpStatus", equalTo(MemberErrorCode.AUTHENTICATION_FAILED.getHttpStatus()))
			.body("code", equalTo(MemberErrorCode.AUTHENTICATION_FAILED.getErrorCode()))
			.body("message", equalTo(MemberErrorCode.AUTHENTICATION_FAILED.getMessage()));
	}

	private void createAuthCode(String email) {
		emailAuthCodeApi.createEmailAuthCode(CreateEmailAuthCodeRequest.of(email));
	}

}
