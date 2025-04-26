package com.gomo.app.member.documentation;

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

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.member.common.util.MemberDBDataHelper;
import com.gomo.app.member.documentation.snippet.LoginMemberSnippet;
import com.gomo.app.member.exception.code.MemberErrorCode;
import com.gomo.app.member.presentation.request.LoginMemberRequest;

@DisplayName("[Presentation documentation]: 회원 로그인 테스트")
public class LoginMemberDocumentationTest extends DocumentationTestBase {

	private static final String URL = "/members/login";

	private final RestDocumentationFilter filter = LoginMemberSnippet.create();
	private final RestDocumentationFilter errorFilter = LoginMemberSnippet.createError();

	private static final String EMAIL = "gomotest@naver.com";
	private static final String PASSWORD = "Gomotest1234@";

	@Autowired
	private MemberDBDataHelper memberDBDataHelper;

	@AfterEach
	void tearDown() {
		memberDBDataHelper.cleanUp();
	}

	@DisplayName("로그인한다.")
	@Test
	void login_member() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.body(LoginMemberRequest.of(EMAIL, PASSWORD))
			.when()
			.post(URL)
			.then()
			.statusCode(OK.value())
			.body("id", hasLength(36))
			.body("token", matchesPattern("^[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+\\.[A-Za-z0-9-_]+$"));
	}

	@DisplayName("존재하지 않는 이메일로 로그인한다.")
	@Test
	void login_member_with_no_exist_email() {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.body(LoginMemberRequest.of("test123@google.com", PASSWORD))
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

	@DisplayName("잘못된 비밀번호로 로그인한다.")
	@Test
	void login_member_with_invalid_password() {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.body(LoginMemberRequest.of(EMAIL, "Wrong123!"))
			.when()
			.post(URL)
			.then()
			.statusCode(MemberErrorCode.AUTHENTICATION_FAILED.getHttpStatus())
			.body("timestamp", instanceOf(String.class))
			.body("path", equalTo(URL))
			.body("httpStatus", equalTo(MemberErrorCode.AUTHENTICATION_FAILED.getHttpStatus()))
			.body("code", equalTo(MemberErrorCode.AUTHENTICATION_FAILED.getErrorCode()))
			.body("message", equalTo(MemberErrorCode.AUTHENTICATION_FAILED.getMessage()));
	}
}
