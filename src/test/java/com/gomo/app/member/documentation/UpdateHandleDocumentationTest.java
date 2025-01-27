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
import com.gomo.app.common.fixture.TestMemberFixture;
import com.gomo.app.common.util.LoginMemberHelper;
import com.gomo.app.member.common.constant.NonExistMemberField;
import com.gomo.app.member.common.util.MemberDBDataHelper;
import com.gomo.app.member.documentation.snippet.UpdateHandleSnippet;
import com.gomo.app.member.presentation.request.UpdateHandleRequest;

public class UpdateHandleDocumentationTest extends DocumentationTestBase {

	private static final String HANDLE_URL = "/members/handles";
	private static final String INVALID_HANDLE = "@G";

	private final RestDocumentationFilter filter = UpdateHandleSnippet.create();
	private final RestDocumentationFilter errorFilter = UpdateHandleSnippet.createError();

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

	@DisplayName("사용자가 핸들을 변경한다.")
	@Test
	void update_handle() {
		given(this.specification).filter(filter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.sessionId(sessionId)
			.body(UpdateHandleRequest.of(NonExistMemberField.HANDLE))
			.when()
			.put(HANDLE_URL)
			.then()
			.statusCode(OK.value());
	}

	@DisplayName("사용자가 중복된 핸들로 변경한다.")
	@Test
	void update_handle_with_duplicated_handle() {
		given(this.specification).filter(errorFilter)
			.header("Content-type", "application/json")
			.sessionId(sessionId)
			.body(UpdateHandleRequest.of(TestMemberFixture.handle()))
			.when()
			.put(HANDLE_URL)
			.then()
			.statusCode(CONFLICT.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo("409"))
			.body("code", equalTo(HANDLE_DUPLICATED.name()))
			.body("message", equalTo("Handle already exists: " + TestMemberFixture.handle()))
			.body("path", equalTo(HANDLE_URL));
	}

	@DisplayName("사용자가 잘못된 핸들로 변경한다.")
	@Test
	void update_handle_with_invalid_handle() {
		given(this.specification).filter(errorFilter)
			.header("Content-type", "application/json")
			.sessionId(sessionId)
			.body(UpdateHandleRequest.of(INVALID_HANDLE))
			.when()
			.put(HANDLE_URL)
			.then()
			.statusCode(UNPROCESSABLE_ENTITY.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo(INVALID_PARAMETER.getHttpStatus()))
			.body("code", equalTo(INVALID_PARAMETER.name()))
			.body("message", equalTo("Invalid parameter: " + INVALID_HANDLE))
			.body("path", equalTo(HANDLE_URL));
	}
}
