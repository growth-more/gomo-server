package com.gomo.app.member.documentation;

import static com.gomo.app.common.exception.DomainErrorCode.*;
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
import com.gomo.app.member.documentation.snippet.SignUpMemberSnippet;
import com.gomo.app.member.presentation.request.CreateMemberRequest;

@DisplayName("[Presentation documentation]: 회원 가입 테스트")
public class SignUpMemberDocumentationTest extends DocumentationTestBase {

	private static final String MEMBER_URL = "/members";
	private final static String SHORT_PASSWORD = "gomo123";

	private final RestDocumentationFilter filter = SignUpMemberSnippet.create();
	private final RestDocumentationFilter errorFilter = SignUpMemberSnippet.createError();

	private static final String EMAIL = "gomotest3@naver.com";
	private static final String PASSWORD = "Gomotest1234@";
	private static final String HANDLE = "@GOMOTEST3";
	private static final String NAME = "gomotest3";
	private static final String MOTTO = "gomotest fighting!";


	@Autowired
	private MemberDBDataHelper memberDBDataHelper;

	@AfterEach
	void tearDown() {
		memberDBDataHelper.cleanUp();
	}

	@DisplayName("사용자가 회원가입한다.")
	@Test
	void sign_up() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.body(CreateMemberRequest.of(EMAIL, PASSWORD, HANDLE, NAME, MOTTO))
			.when()
			.post(MEMBER_URL)
			.then()
			.statusCode(CREATED.value())
			.body("id", hasLength(36));
	}

	@DisplayName("사용자가 잘못된 형식의 정보로 회원가입한다.")
	@Test
	void sign_up_member_with_short_password() {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.body(CreateMemberRequest.of(EMAIL, SHORT_PASSWORD, HANDLE, NAME, MOTTO))
			.when()
			.post(MEMBER_URL)
			.then()
			.statusCode(UNPROCESSABLE_ENTITY.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo(INVALID_PARAMETER.getHttpStatus()))
			.body("code", equalTo(INVALID_PARAMETER.name()))
			.body("message", equalTo("password must at least 8 characters"))
			.body("path", equalTo(MEMBER_URL));
	}
}
