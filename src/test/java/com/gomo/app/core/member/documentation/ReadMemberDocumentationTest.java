package com.gomo.app.core.member.documentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.core.member.documentation.snippet.ReadMemberSnippet;

@DisplayName("[Presentation Documentation]: 사용자 정보 조회 테스트")
public class ReadMemberDocumentationTest extends DocumentationTestBase {

	private static final String MEMBER_URL = "/members";

	private final RestDocumentationFilter filter = ReadMemberSnippet.create();

	@DisplayName("사용자가 사용자의 프로필 정보를 조회한다.")
	@Test
	void read_profile() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.when()
			.get(MEMBER_URL)
			.then()
			.statusCode(OK.value())
			.body("id", hasLength(36))
			.body("email", equalTo("testmember@naver.com"))
			.body("handle", equalTo("@Test"))
			.body("name", equalTo("testname"))
			.body("motto", equalTo("testmotto"))
			.body("availablePoints", equalTo(0))
			.body("profileImageUrl", equalTo("DEFAULT_IMAGE"))
			.body("profileBannerUrl", equalTo("DEFAULT_IMAGE"))
			.body("loginProvider", equalTo("EMAIL"))
			.body("roleType", equalTo("ROLE_MEMBER"))
			.body("subscriptionPlan", equalTo("FREE"))
			.body("activateStatus", equalTo("ACTIVE"))
			.body("signUpDateTime", notNullValue());
	}
}
