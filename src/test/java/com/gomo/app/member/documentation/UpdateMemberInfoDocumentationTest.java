package com.gomo.app.member.documentation;

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
import com.gomo.app.member.documentation.snippet.UpdateMemberInfoSnippet;
import com.gomo.app.member.presentation.request.UpdateMemberRequest;
import com.gomo.app.member.presentation.request.UpdatePasswordRequest;

public class UpdateMemberInfoDocumentationTest extends DocumentationTestBase {

	private static final String MEMBER_URL = "/members";
	private static final String INVALID_NAME = "~ ! @ # $";
	private static final String INVALID_MOTTO = "~!@#$";

	private final RestDocumentationFilter filter = UpdateMemberInfoSnippet.create();
	private final RestDocumentationFilter errorFilter = UpdateMemberInfoSnippet.createError();

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

	@DisplayName("사용자가 이름과 한 줄 다짐을 변경한다.")
	@Test
	void update_member_name_and_motto() {
		given(this.specification).filter(filter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.sessionId(sessionId)
			.body(UpdateMemberRequest.of(NonExistMemberField.NAME, NonExistMemberField.MOTTO))
			.when()
			.put(MEMBER_URL)
			.then()
			.statusCode(OK.value());
	}

	@DisplayName("사용자가 잘못된 이름으로 개인 정보를 변경한다.")
	@Test
	void update_member_info_with_invalid_name() {
		given(this.specification).filter(errorFilter)
			.header("Content-type", "application/json")
			.sessionId(sessionId)
			.body(UpdatePasswordRequest.of(INVALID_NAME, NonExistMemberField.MOTTO))
			.when()
			.put(MEMBER_URL)
			.then()
			.statusCode(UNPROCESSABLE_ENTITY.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo("422"))
			.body("code", equalTo(INVALID_PARAMETER.name()))
			.body("message", equalTo("Invalid parameter: " + INVALID_NAME))
			.body("path", equalTo(MEMBER_URL));
	}

	@DisplayName("사용자가 잘못된 모토로 개인 정보를 변경한다.")
	@Test
	void update_member_info_with_invalid_motto() {
		given(this.specification).filter(errorFilter)
			.header("Content-type", "application/json")
			.sessionId(sessionId)
			.body(UpdatePasswordRequest.of(NonExistMemberField.NAME, INVALID_MOTTO))
			.when()
			.put(MEMBER_URL)
			.then()
			.statusCode(UNPROCESSABLE_ENTITY.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo("422"))
			.body("code", equalTo(INVALID_PARAMETER.name()))
			.body("message", equalTo("Invalid parameter: " + INVALID_MOTTO))
			.body("path", equalTo(MEMBER_URL));
	}
}
