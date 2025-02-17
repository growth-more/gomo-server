package com.gomo.app.member.documentation;

import static com.gomo.app.member.exception.MemberErrorCode.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.member.documentation.snippet.LoginMemberSnippet;
import com.gomo.app.member.presentation.request.LoginMemberRequest;

public class LoginMemberDocumentationTest extends DocumentationTestBase {

	private static final String LOGIN_MEMBER_URL = "/members/login";

	private final RestDocumentationFilter filter = LoginMemberSnippet.create();
	private final RestDocumentationFilter errorFilter = LoginMemberSnippet.createError();

	@DisplayName("사용자가 로그인한다.")
	@Test
	void login_member() {
		given(this.specification).filter(filter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.body(LoginMemberRequest.of(TestMemberFixture.email(), TestMemberFixture.password()))
			.when()
			.post(LOGIN_MEMBER_URL)
			.then()
			.statusCode(CREATED.value())
			.body("id", hasLength(36))
			.body("email", equalTo(TestMemberFixture.email()))
			.body("handle", equalTo(TestMemberFixture.password()))
			.body("name", equalTo(TestMemberFixture.name()));
	}

	@DisplayName("사용자가 존재하지 않는 이메일로 로그인한다.")
	@Test
	void login_member_with_no_exist_email() {
		given(this.specification).filter(errorFilter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.body(LoginMemberRequest.of(EMAIL, PASSWORD))
			.when()
			.post(LOGIN_MEMBER_URL)
			.then()
			.statusCode(UNAUTHORIZED.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo("401"))
			.body("code", equalTo(AUTHENTICATION_FAILED.name()))
			.body("message", equalTo("Member Authentication fail"))
			.body("path", equalTo(LOGIN_MEMBER_URL));
	}

	@DisplayName("사용자가 잘못된 비밀번호로 로그인한다.")
	@Test
	void login_member_with_invalid_password() {
		given(this.specification).filter(errorFilter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.body(LoginMemberRequest.of(EMAIL, PASSWORD))
			.when()
			.post(LOGIN_MEMBER_URL)
			.then()
			.statusCode(UNAUTHORIZED.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo("401"))
			.body("code", equalTo(AUTHENTICATION_FAILED.name()))
			.body("message", equalTo("Member Authentication fail"))
			.body("path", equalTo(LOGIN_MEMBER_URL));
	}
}
