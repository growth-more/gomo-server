package com.gomo.app.member.documentation;

import static com.gomo.app.common.exception.DomainErrorCode.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.member.common.util.MemberDBDataHelper;
import com.gomo.app.member.documentation.snippet.SignUpMemberSnippet;
import com.gomo.app.member.presentation.request.CreateMemberRequest;

public class SignUpMemberDocumentationTest extends DocumentationTestBase {

	private static final String MEMBER_URL = "/members";
	private final static String SHORT_PASSWORD = "gomo123";

	private final RestDocumentationFilter filter = SignUpMemberSnippet.create();
	private final RestDocumentationFilter errorFilter = SignUpMemberSnippet.createError();

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
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.body(CreateMemberRequest.of(EMAIL, PASSWORD, HANDLE, NAME, MOTTO))
			.when()
			.post(MEMBER_URL)
			.then()
			.statusCode(CREATED.value())
			.body("id", hasLength(36));
	}

	/**
	 * 테스트 작성 시, 다음 주의사항을 참고한다.
	 * 1. 성공 케이스는 filter를 사용한다.
	 * 2. 실패 케이스는 errorFilter를 사용한다.
	 */
	@DisplayName("사용자가 OAuth를 통해 회원가입한다.")
	@Test
	void sign_up_with_OAuth() throws JsonProcessingException {

	}

	@DisplayName("사용자가 이메일 인증을 하지 않고 회원가입한다.")
	@Test
	void sign_up_without_email_validation() throws JsonProcessingException {

	}

	@DisplayName("사용자가 핸들 중복을 확인하지 않고 회원가입한다.")
	@Test
	void sign_up_without_check_duplicated_handle() throws JsonProcessingException {

	}

	@DisplayName("사용자가 잘못된 형식의 정보로 회원가입한다.")
	@Test
	void sign_up_member_with_short_password() {
		given(this.specification).filter(errorFilter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
			.body(CreateMemberRequest.of(EMAIL, SHORT_PASSWORD, HANDLE, NAME, MOTTO))
			.when()
			.post(MEMBER_URL)
			.then()
			.statusCode(UNPROCESSABLE_ENTITY.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo(INVALID_PARAMETER.getHttpStatus()))
			.body("code", equalTo(INVALID_PARAMETER.name()))
			.body("message", equalTo("Invalid parameter: " + SHORT_PASSWORD.length()))
			.body("path", equalTo(MEMBER_URL));
	}
}
