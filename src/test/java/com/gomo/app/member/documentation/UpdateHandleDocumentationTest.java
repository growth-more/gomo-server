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
import com.gomo.app.member.documentation.snippet.UpdateHandleSnippet;
import com.gomo.app.member.exception.code.HandleErrorCode;
import com.gomo.app.member.presentation.request.UpdateHandleRequest;

@DisplayName("[Presentation documentation]: 핸들 변경 테스트")
public class UpdateHandleDocumentationTest extends DocumentationTestBase {

	private static final String HANDLE_URL = "/members/handles";

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

	@DisplayName("핸들을 변경한다.")
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

	@DisplayName("중복된 핸들로 변경한다.")
	@Test
	void update_handle_with_duplicated_handle() {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + token)
			.body(UpdateHandleRequest.of("@GOMOTEST2"))
			.when()
			.put(HANDLE_URL)
			.then()
			.statusCode(HandleErrorCode.DUPLICATED.getHttpStatus())
			.body("timestamp", instanceOf(String.class))
			.body("path", equalTo(HANDLE_URL))
			.body("httpStatus", equalTo(HandleErrorCode.DUPLICATED.getHttpStatus()))
			.body("code", equalTo(HandleErrorCode.DUPLICATED.getErrorCode()))
			.body("message", equalTo(HandleErrorCode.DUPLICATED.getMessage()));
	}

	@DisplayName("최소 길이보다 짧은 핸들로 변경한다.")
	@Test
	void update_handle_with_invalid_handle() {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + token)
			.body(UpdateHandleRequest.of("@G"))
			.when()
			.put(HANDLE_URL)
			.then()
			.statusCode(HandleErrorCode.TOO_SHORT.getHttpStatus())
			.body("timestamp", instanceOf(String.class))
			.body("path", equalTo(HANDLE_URL))
			.body("httpStatus", equalTo(HandleErrorCode.TOO_SHORT.getHttpStatus()))
			.body("code", equalTo(HandleErrorCode.TOO_SHORT.getErrorCode()))
			.body("message", equalTo(HandleErrorCode.TOO_SHORT.getMessage()));
	}
}
