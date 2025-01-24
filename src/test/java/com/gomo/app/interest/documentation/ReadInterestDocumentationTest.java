package com.gomo.app.interest.documentation;

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
import com.gomo.app.interest.common.fixture.interest.BackendInterestFixture;
import com.gomo.app.interest.documentation.snippet.ReadInterestSnippet;

public class ReadInterestDocumentationTest extends DocumentationTestBase {

	private static final String INTEREST_URL = "/interests/{interestId}";

	private final RestDocumentationFilter filter = ReadInterestSnippet.create();

	@Autowired
	private LoginMemberHelper loginHelper;

	@BeforeEach
	public void setUp() {
		sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
	}

	@DisplayName("사용자가 하나의 관심사(Backend)를 조회한다.")
	@Test
	void read_interest() {
		given(this.specification).filter(filter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.when()
			.get(INTEREST_URL, BackendInterestFixture.id())
			.then()
			.statusCode(CREATED.value())
			.body("interestId", instanceOf(String.class))
			.body("memberId", instanceOf(String.class))
			.body("name", equalTo(BackendInterestFixture.name()))
			.body("logoUrl", equalTo(BackendInterestFixture.logoUrl()))
			.body("level", equalTo(BackendInterestFixture.level()))
			.body("score", equalTo(BackendInterestFixture.score()))
			.body("totalScore", equalTo(BackendInterestFixture.totalScore()));
	}
}
