package com.gomo.app.member.documentation;

import static com.gomo.app.common.exception.DomainErrorCode.INVALID_PARAMETER;
import static com.gomo.app.member.exception.MemberErrorCode.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.common.util.LoginMemberHelper;
import com.gomo.app.member.common.util.MemberDBDataHelper;
import com.gomo.app.member.documentation.snippet.UpdatePasswordSnippet;
import com.gomo.app.member.presentation.request.UpdatePasswordRequest;

public class UpdatePasswordDocumentationTest extends DocumentationTestBase {

	private static final String PASSWORD_URL = "/members/passwords";
	private static final String INVALID_PASSWORD = "~ ! @ # $";

	private final RestDocumentationFilter filter = UpdatePasswordSnippet.create();
	private final RestDocumentationFilter errorFilter = UpdatePasswordSnippet.createError();

	private String sessionId;

	@Autowired
	LoginMemberHelper loginMemberHelper;

	@Autowired
	MemberDBDataHelper memberDBDataHelper;

	@BeforeEach
	public void setUp() {
		sessionId = loginMemberHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
	}

	@AfterEach
	public void tearDown() {
		memberDBDataHelper.cleanUp();
	}

	// TODO <jhl221123>: 비밀번호 암호화 기능 추가 후 수정 필요
	@DisplayName("사용자가 비밀번호를 변경한다.")
	@Test
	void update_password() {
		given(this.specification).filter(filter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.sessionId(sessionId)
			.body(UpdatePasswordRequest.of(TestMemberFixture.password(), NonExistMemberField.PASSWORD))
			.when()
			.put(PASSWORD_URL)
			.then()
			.statusCode(OK.value());
	}

	@DisplayName("사용자가 기존 비밀번호를 잘못 입력한 상태로 변경한다.")
	@Test
	void update_password_with_invalid_origin_password() {
		given(this.specification).filter(errorFilter)
			.header("Content-type", "application/json")
			.sessionId(sessionId)
			.body(UpdatePasswordRequest.of(NonExistMemberField.PASSWORD, NonExistMemberField.PASSWORD))
			.when()
			.put(PASSWORD_URL)
			.then()
			.statusCode(UNAUTHORIZED.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo("401"))
			.body("code", equalTo(AUTHENTICATION_FAILED.name()))
			.body("message", equalTo("Member Authentication fail"))
			.body("path", equalTo(PASSWORD_URL));
	}

	@DisplayName("사용자가 잘못된 새 비밀번호로 변경한다.")
	@Test
	void update_password_with_invalid_updated_password() {
		given(this.specification).filter(errorFilter)
			.header("Content-type", "application/json")
			.sessionId(sessionId)
			.body(UpdatePasswordRequest.of(TestMemberFixture.password(), INVALID_PASSWORD))
			.when()
			.put(PASSWORD_URL)
			.then()
			.statusCode(UNPROCESSABLE_ENTITY.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo(INVALID_PARAMETER.getHttpStatus()))
			.body("code", equalTo(INVALID_PARAMETER.name()))
			.body("message", equalTo("Invalid parameter: " + INVALID_PASSWORD))
			.body("path", equalTo(PASSWORD_URL));
	}
}
