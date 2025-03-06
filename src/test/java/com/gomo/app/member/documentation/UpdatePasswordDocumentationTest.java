package com.gomo.app.member.documentation;

import static com.gomo.app.common.exception.DomainErrorCode.INVALID_PARAMETER;
import static com.gomo.app.member.exception.MemberErrorCode.*;
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
import com.gomo.app.member.presentation.request.UpdatePasswordRequest;

@DisplayName("[Presentation documentation]: 비밀번호 변경 테스트")
public class UpdatePasswordDocumentationTest extends DocumentationTestBase {

	private static final String PASSWORD_URL = "/members/passwords";
	private static final String INVALID_PASSWORD = "~ ! @ # $";

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

	@DisplayName("사용자가 비밀번호를 변경한다.")
	@Test
	void update_password() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + token)
			.body(UpdatePasswordRequest.of(PASSWORD, "Gomotest1234!"))
			.when()
			.put(PASSWORD_URL)
			.then()
			.statusCode(NO_CONTENT.value());
	}

	@DisplayName("사용자가 기존 비밀번호를 잘못 입력한 상태로 변경한다.")
	@Test
	void update_password_with_invalid_origin_password() {
		given(this.specification).filter(errorFilter)
			.header("Content-type", "application/json")
			.header(AUTHORIZATION, "Bearer " + token)
			.body(UpdatePasswordRequest.of("Gomotest123@", "Gomotest1234!"))
			.when()
			.put(PASSWORD_URL)
			.then()
			.statusCode(UNAUTHORIZED.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo(UNAUTHORIZED.value()))
			.body("code", equalTo(AUTHENTICATION_FAILED.name()))
			.body("message", equalTo("password incorrect"))
			.body("path", equalTo(PASSWORD_URL));
	}

	@DisplayName("사용자가 잘못된 새 비밀번호로 변경한다.")
	@Test
	void update_password_with_invalid_updated_password() {
		given(this.specification).filter(errorFilter)
			.header("Content-type", "application/json")
			.header(AUTHORIZATION, "Bearer " + token)
			.body(UpdatePasswordRequest.of(PASSWORD, INVALID_PASSWORD))
			.when()
			.put(PASSWORD_URL)
			.then()
			.statusCode(UNPROCESSABLE_ENTITY.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo(INVALID_PARAMETER.getHttpStatus()))
			.body("code", equalTo(INVALID_PARAMETER.name()))
			.body("message", equalTo("password must comply with the password rules."))
			.body("path", equalTo(PASSWORD_URL));
	}
}
