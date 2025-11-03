package com.gomo.app.core.auth.adapter.in.api;

import static io.restassured.RestAssured.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.core.auth.adapter.in.api.request.LoginRequest;
import com.gomo.app.core.auth.adapter.in.api.snippet.LoginMemberSnippet;
import com.gomo.app.test.DocumentationTestBase;

@DisplayName("[Presentation Documentation]: 회원 로그인 테스트")
public class LoginDocumentationTest extends DocumentationTestBase {

	private static final String URL = "/auth/login";

	private final RestDocumentationFilter filter = LoginMemberSnippet.create();
	private final RestDocumentationFilter errorFilter = LoginMemberSnippet.createError();

	@DisplayName("로그인에 성공한다.")
	@Test
	void login_member() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.body(LoginRequest.of(sessionEmail, sessionPassword))
			.when()
			.post(URL)
			.then()
			.statusCode(OK.value());
	}
}
