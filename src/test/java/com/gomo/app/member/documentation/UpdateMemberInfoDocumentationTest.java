package com.gomo.app.member.documentation;

import static com.gomo.app.common.exception.DomainErrorCode.*;
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
import com.gomo.app.member.documentation.snippet.UpdateMemberInfoSnippet;
import com.gomo.app.member.presentation.request.UpdateMemberRequest;

@DisplayName("[Presentation documentation]: 회원 기본 정보 수정 테스트")
public class UpdateMemberInfoDocumentationTest extends DocumentationTestBase {

	private static final String MEMBER_URL = "/members";
	private static final String INVALID_NAME = "~ ! @ # $";
	private static final String INVALID_MOTTO = "~!@#$";

	private final RestDocumentationFilter filter = UpdateMemberInfoSnippet.create();
	private final RestDocumentationFilter errorFilter = UpdateMemberInfoSnippet.createError();

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

	@DisplayName("사용자가 이름과 한 줄 다짐을 변경한다.")
	@Test
	void update_member_name_and_motto() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + token)
			.body(UpdateMemberRequest.of("GOMO4","GOMOTEST.."))
			.when()
			.put(MEMBER_URL)
			.then()
			.statusCode(NO_CONTENT.value());
	}

	@DisplayName("사용자가 잘못된 이름으로 개인 정보를 변경한다.")
	@Test
	void update_member_info_with_invalid_name() {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + token)
			.body(UpdateMemberRequest.of(INVALID_NAME, "GOMOTEST.."))
			.when()
			.put(MEMBER_URL)
			.then()
			.statusCode(UNPROCESSABLE_ENTITY.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo(INVALID_PARAMETER.getHttpStatus()))
			.body("code", equalTo(INVALID_PARAMETER.name()))
			.body("message", equalTo("name must contain only letters and numbers"))
			.body("path", equalTo(MEMBER_URL));
	}

	@DisplayName("사용자가 잘못된 모토로 개인 정보를 변경한다.")
	@Test
	void update_member_info_with_invalid_motto() {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + token)
			.body(UpdateMemberRequest.of("GOMO4", INVALID_MOTTO))
			.when()
			.put(MEMBER_URL)
			.then()
			.statusCode(UNPROCESSABLE_ENTITY.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo(INVALID_PARAMETER.getHttpStatus()))
			.body("code", equalTo(INVALID_PARAMETER.name()))
			.body("message", equalTo("Motto must comply with the motto rules"))
			.body("path", equalTo(MEMBER_URL));
	}
}
