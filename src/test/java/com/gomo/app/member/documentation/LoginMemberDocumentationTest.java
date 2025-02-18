package com.gomo.app.member.documentation;

import static com.gomo.app.member.exception.MemberErrorCode.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.*;

import com.gomo.app.member.exception.MemberErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.member.documentation.snippet.LoginMemberSnippet;
import com.gomo.app.member.presentation.request.LoginMemberRequest;

@DisplayName("[Presentation documentation]: 회원 로그인 테스트")
public class LoginMemberDocumentationTest extends DocumentationTestBase {

	private static final String LOGIN_MEMBER_URL = "/members/login";

	private final RestDocumentationFilter filter = LoginMemberSnippet.create();
	private final RestDocumentationFilter errorFilter = LoginMemberSnippet.createError();

	private static final String EMAIL = "gomotest@naver.com";
	private static final String PASSWORD = "Gomotest1234@";

	@DisplayName("사용자가 로그인한다.")
	@Test
	void login_member() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.body(LoginMemberRequest.of(EMAIL, PASSWORD))
			.when()
			.post(LOGIN_MEMBER_URL)
			.then()
			.statusCode(OK.value())
			.body("id", hasLength(36))
			.body("token", matchesPattern("^[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+$"));
	}

	@DisplayName("사용자가 존재하지 않는 이메일로 로그인한다.")
	@Test
	void login_member_with_no_exist_email() {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.body(LoginMemberRequest.of("test123@google.com", PASSWORD))
			.when()
			.post(LOGIN_MEMBER_URL)
			.then()
			.statusCode(NOT_FOUND.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo(NOT_FOUND.value()))
			.body("code", equalTo(MemberErrorCode.NOT_FOUND.name()))
			.body("message", equalTo("check email or password"))
			.body("path", equalTo(LOGIN_MEMBER_URL));
	}

	@DisplayName("사용자가 잘못된 비밀번호로 로그인한다.")
	@Test
	void login_member_with_invalid_password() {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.body(LoginMemberRequest.of(EMAIL, "Wrong123!"))
			.when()
			.post(LOGIN_MEMBER_URL)
			.then()
			.statusCode(UNAUTHORIZED.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo(UNAUTHORIZED.value()))
			.body("code", equalTo(AUTHENTICATION_FAILED.name()))
			.body("message", equalTo("password incorrect"))
			.body("path", equalTo(LOGIN_MEMBER_URL));
	}
}
