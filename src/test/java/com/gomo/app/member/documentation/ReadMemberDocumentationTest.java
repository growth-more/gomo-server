package com.gomo.app.member.documentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.*;

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
import com.gomo.app.member.documentation.snippet.ReadMemberSnippet;

public class ReadMemberDocumentationTest extends DocumentationTestBase {

	private static final String MEMBER_URL = "/members";

	private final RestDocumentationFilter filter = ReadMemberSnippet.create();

	@Autowired
	LoginMemberHelper loginMemberHelper;

	@BeforeEach
	public void setUp() {
		sessionId = loginMemberHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
	}

	@DisplayName("사용자가 프로필 페이지에서 프로필 정보를 조회한다.")
	@Test
	void read_profile() {
		given(this.specification).filter(filter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.sessionId(sessionId)
			.when()
			.get(MEMBER_URL)
			.then()
			.statusCode(OK.value())
			.body("id", hasLength(36))
			.body("email", equalTo(TestMemberFixture.email()))
			.body("handle", equalTo(TestMemberFixture.handle()))
			.body("name", equalTo(TestMemberFixture.name()))
			.body("motto", equalTo(TestMemberFixture.motto()))
			.body("availablePoints", is(TestMemberFixture.availablePoints()))
			.body("profileImageUrl", equalTo(TestMemberFixture.profileImageUrl()))
			.body("profileImageName", equalTo(TestMemberFixture.profileImageName()))
			.body("roleType", equalTo(TestMemberFixture.roleType().name()))
			.body("subscriptionPlan", equalTo(TestMemberFixture.subscriptionPlan().name()))
			.body("activateStatus", equalTo(TestMemberFixture.activateStatus().name()))
			.body("signUpDateTime", equalTo(TestMemberFixture.signUpDateTime())).log().all();
	}
}
