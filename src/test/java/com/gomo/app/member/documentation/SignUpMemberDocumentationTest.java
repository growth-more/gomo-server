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
import com.gomo.app.member.documentation.snippet.SignUpMemberSnippet;
import com.gomo.app.member.exception.code.PasswordErrorCode;
import com.gomo.app.member.presentation.request.CreateMemberRequest;

@DisplayName("[Presentation documentation]: 회원 가입 테스트")
public class SignUpMemberDocumentationTest extends DocumentationTestBase {

	private static final String MEMBER_URL = "/members";

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

	@DisplayName("회원 가입한다.")
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

	@DisplayName("최소 길이보다 짧은 비밀번호로 회원가입한다.")
	@Test
	void sign_up_member_with_short_password() {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.body(CreateMemberRequest.of(EMAIL, "gomo123", HANDLE, NAME, MOTTO))
			.when()
			.post(MEMBER_URL)
			.then()
			.statusCode(PasswordErrorCode.TOO_SHORT.getHttpStatus())
			.body("timestamp", instanceOf(String.class))
			.body("path", equalTo(MEMBER_URL))
			.body("httpStatus", equalTo(PasswordErrorCode.TOO_SHORT.getHttpStatus()))
			.body("code", equalTo(PasswordErrorCode.TOO_SHORT.getErrorCode()))
			.body("message", equalTo(PasswordErrorCode.TOO_SHORT.getMessage()));
	}
}
