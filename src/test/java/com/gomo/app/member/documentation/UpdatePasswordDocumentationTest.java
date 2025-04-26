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
import com.gomo.app.common.util.LoginMemberHelper;
import com.gomo.app.member.common.util.MemberDBDataHelper;
import com.gomo.app.member.documentation.snippet.UpdatePasswordSnippet;
import com.gomo.app.member.exception.code.MemberErrorCode;
import com.gomo.app.member.exception.code.PasswordErrorCode;
import com.gomo.app.member.presentation.request.UpdatePasswordRequest;

@DisplayName("[Presentation documentation]: 비밀번호 변경 테스트")
public class UpdatePasswordDocumentationTest extends DocumentationTestBase {

	private static final String URL = "/members/passwords";

	private final RestDocumentationFilter filter = UpdatePasswordSnippet.create();
	private final RestDocumentationFilter errorFilter = UpdatePasswordSnippet.createError();

	private static final String EMAIL = "gomotest@naver.com";
	private static final String PASSWORD = "Gomotest1234@";

	private String token;

	@Autowired
	LoginMemberHelper loginMemberHelper;

	@Autowired
	MemberDBDataHelper memberDBDataHelper;

	@BeforeEach
	public void setUp() {
		token = loginMemberHelper.getAccessToken(EMAIL, PASSWORD);
	}

	@AfterEach
	public void tearDown() {
		memberDBDataHelper.cleanUp();
	}

	@DisplayName("비밀번호를 변경한다.")
	@Test
	void update_password() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + token)
			.body(UpdatePasswordRequest.of(PASSWORD, "Gomotest1234!"))
			.when()
			.put(URL)
			.then()
			.statusCode(NO_CONTENT.value());
	}

	@DisplayName("기존 비밀번호를 잘못 입력한 상태로 변경한다.")
	@Test
	void update_password_with_incorrect_origin_password() {
		given(this.specification).filter(errorFilter)
			.header("Content-type", "application/json")
			.header(AUTHORIZATION, "Bearer " + token)
			.body(UpdatePasswordRequest.of("incorrect1234!", "Gomotest1234!"))
			.when()
			.put(URL)
			.then()
			.statusCode(MemberErrorCode.AUTHENTICATION_FAILED.getHttpStatus())
			.body("timestamp", instanceOf(String.class))
			.body("path", equalTo(URL))
			.body("httpStatus", equalTo(MemberErrorCode.AUTHENTICATION_FAILED.getHttpStatus()))
			.body("code", equalTo(MemberErrorCode.AUTHENTICATION_FAILED.getErrorCode()))
			.body("message", equalTo(MemberErrorCode.AUTHENTICATION_FAILED.getMessage()));
	}

	@DisplayName("금지 문자가 포함된 새 비밀번호로 변경한다.")
	@Test
	void update_password_with_forbidden_updated_password() {
		given(this.specification).filter(errorFilter)
			.header("Content-type", "application/json")
			.header(AUTHORIZATION, "Bearer " + token)
			.body(UpdatePasswordRequest.of(PASSWORD, "! @ ~ # *"))
			.when()
			.put(URL)
			.then()
			.statusCode(PasswordErrorCode.FORBIDDEN.getHttpStatus())
			.body("timestamp", instanceOf(String.class))
			.body("path", equalTo(URL))
			.body("httpStatus", equalTo(PasswordErrorCode.FORBIDDEN.getHttpStatus()))
			.body("code", equalTo(PasswordErrorCode.FORBIDDEN.getErrorCode()))
			.body("message", equalTo(PasswordErrorCode.FORBIDDEN.getMessage()));
	}
}
