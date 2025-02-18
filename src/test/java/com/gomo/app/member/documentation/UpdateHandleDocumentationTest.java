package com.gomo.app.member.documentation;

import static com.gomo.app.common.exception.DomainErrorCode.INVALID_PARAMETER;
import static com.gomo.app.member.exception.MemberErrorCode.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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
import com.gomo.app.member.documentation.snippet.UpdateHandleSnippet;
import com.gomo.app.member.presentation.request.UpdateHandleRequest;

@DisplayName("[Presentation documentation]: 핸들 변경 테스트")
public class UpdateHandleDocumentationTest extends DocumentationTestBase {

	private static final String HANDLE_URL = "/members/handles";
	private static final String INVALID_HANDLE = "@G";

	private final RestDocumentationFilter filter = UpdateHandleSnippet.create();
	private final RestDocumentationFilter errorFilter = UpdateHandleSnippet.createError();

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

	@DisplayName("사용자가 핸들을 변경한다.")
	@Test
	void update_handle() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + token)
			.body(UpdateHandleRequest.of("@UPDATE"))
			.when()
			.put(HANDLE_URL)
			.then()
			.statusCode(NO_CONTENT.value());
	}

	@DisplayName("사용자가 중복된 핸들로 변경한다.")
	@Test
	void update_handle_with_duplicated_handle() {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + token)
			.body(UpdateHandleRequest.of("@GOMOTEST2"))
			.when()
			.put(HANDLE_URL)
			.then()
			.statusCode(CONFLICT.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo(CONFLICT.value()))
			.body("code", equalTo(HANDLE_DUPLICATED.name()))
			.body("message", equalTo("handle already exists"))
			.body("path", equalTo(HANDLE_URL));
	}

	@DisplayName("사용자가 잘못된 핸들로 변경한다.")
	@Test
	void update_handle_with_invalid_handle() {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + token)
			.body(UpdateHandleRequest.of(INVALID_HANDLE))
			.when()
			.put(HANDLE_URL)
			.then()
			.statusCode(UNPROCESSABLE_ENTITY.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo(INVALID_PARAMETER.getHttpStatus()))
			.body("code", equalTo(INVALID_PARAMETER.name()))
			.body("message", equalTo("Handle must be at least 3 characters"))
			.body("path", equalTo(HANDLE_URL));
	}
}
