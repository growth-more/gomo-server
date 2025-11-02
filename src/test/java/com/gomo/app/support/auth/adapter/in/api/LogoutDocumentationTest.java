package com.gomo.app.support.auth.adapter.in.api;

import static io.restassured.RestAssured.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.support.auth.adapter.in.api.snippet.LogoutMemberSnippet;
import com.gomo.app.test.DocumentationTestBase;

@DisplayName("[Presentation Documentation]: 회원 로그아웃 테스트")
public class LogoutDocumentationTest extends DocumentationTestBase {
	private static final String LOGOUT_MEMBER_URL = "/auth/logout";

	private final RestDocumentationFilter filter = LogoutMemberSnippet.create();
	private final RestDocumentationFilter errorFilter = LogoutMemberSnippet.createError();

	@DisplayName("사용자가 로그아웃을 시도한다.")
	@Test
	void test_logout_member() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.when()
			.get(LOGOUT_MEMBER_URL)
			.then()
			.statusCode(OK.value());
	}
}
