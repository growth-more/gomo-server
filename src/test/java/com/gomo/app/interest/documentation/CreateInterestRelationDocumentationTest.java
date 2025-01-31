package com.gomo.app.interest.documentation;

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
import com.gomo.app.interest.common.util.InterestRelationDBDataHelper;
import com.gomo.app.interest.documentation.snippet.CreateInterestRelationSnippet;

public class CreateInterestRelationDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = CreateInterestRelationSnippet.create();

	@Autowired
	private LoginMemberHelper loginHelper;

	@Autowired
	private InterestRelationDBDataHelper interestRelationDBDataHelper;

	@BeforeEach
	public void setUp() {
		sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
	}

	@AfterEach
	public void tearDown() {
		interestRelationDBDataHelper.cleanUp();
	}

	@DisplayName("사용자가 관심사 간의 계층 구조를 추가한다.")
	@Test
	void create_interest_relation() {
		given(this.specification).filter(filter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.sessionId(sessionId)
			.when()
			.post("/interests/networks/relations")
			.then()
			.statusCode(CREATED.value())
			.body("id", hasLength(36));
	}
}
