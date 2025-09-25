package com.gomo.app.auth.documentation;

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

import com.gomo.app.auth.documentation.snippet.VerifyEmailAuthCodeSnippet;
import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.core.member.exception.code.MemberErrorCode;
import com.gomo.app.core.member.presentation.EmailCodeApi;
import com.gomo.app.core.member.presentation.request.CreateEmailCodeRequest;
import com.gomo.app.support.auth.domain.repository.AuthCodeRepository;

@DisplayName("[Presentation Documentation]: 이메일 인증 코드 테스트")
public class VerifyEmailAuthCodeDocumentationTest extends DocumentationTestBase {

	private static final String EMAIL_VERIFY_URL = "/auth/codes/verify";

	private final RestDocumentationFilter filter = VerifyEmailAuthCodeSnippet.create();
	private final RestDocumentationFilter errorFilter = VerifyEmailAuthCodeSnippet.createError();

	private static final String EMAIL = "test@test.com";

	@Autowired
	EmailCodeApi emailCodeApi;

	@Autowired
	AuthCodeRepository authCodeRepository;

	private String AUTHCODE = "";

	@BeforeEach
	void setup() {
		createAuthCode(EMAIL);
		AUTHCODE = authCodeRepository.findByEmail(EMAIL).get();
	}

	@AfterEach
	void teardown() {
		authCodeRepository.delete(EMAIL);
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
		emailCodeApi.create(CreateEmailCodeRequest.of(email));
	}

}
