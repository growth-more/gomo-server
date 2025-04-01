package com.gomo.app.member.documentation;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.common.util.LoginMemberHelper;
import com.gomo.app.member.documentation.snippet.ReadMemberSnippet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.*;

@DisplayName("[Presentation documentation]: 회원 조회 테스트")
public class ReadMemberDocumentationTest extends DocumentationTestBase {

	private static final String MEMBER_URL = "/members";

	private final RestDocumentationFilter filter = ReadMemberSnippet.create();

	private static final String EMAIL = "gomotest@naver.com";
	private static final String PASSWORD = "Gomotest1234@";

	private String token;

	@Autowired
	LoginMemberHelper loginMemberHelper;

	@BeforeEach
	public void setUp() {
		token = loginMemberHelper.getAccessToken(EMAIL, PASSWORD);
	}

	@DisplayName("사용자가 프로필 페이지에서 프로필 정보를 조회한다.")
	@Test
	void read_profile() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + token)
			.when()
			.get(MEMBER_URL)
			.then()
			.statusCode(OK.value())
			.body("id", hasLength(36))
			.body("email", equalTo("gomotest@naver.com"))
			.body("handle", equalTo("@GOMOTEST"))
			.body("name", equalTo("gomotest"))
			.body("motto", equalTo("gomotest fighting!"))
			.body("availablePoints", equalTo(1660))
			.body("profileImageUrl", equalTo("https://mini-cloud/gomotest-profile"))
			.body("loginProvider", equalTo("EMAIL"))
			.body("roleType", equalTo("ROLE_MEMBER"))
			.body("subscriptionPlan", equalTo("FREE"))
			.body("activateStatus", equalTo("ACTIVE"))
			.body("signUpDateTime", notNullValue());
	}
}
