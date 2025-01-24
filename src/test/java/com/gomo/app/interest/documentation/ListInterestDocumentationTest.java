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
import com.gomo.app.interest.common.fixture.interest.JavaInterestFixture;
import com.gomo.app.interest.common.fixture.interest.SpringInterestFixture;
import com.gomo.app.interest.documentation.snippet.ListInterestSnippet;

public class ListInterestDocumentationTest extends DocumentationTestBase {

	private static final String LIST_INTEREST_URL = "/interests";

	private final RestDocumentationFilter filter = ListInterestSnippet.create();

	@Autowired
	private LoginMemberHelper loginHelper;

	@BeforeEach
	public void setUp() {
		sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
	}

	// TODO <jhl221123>: id 외 다른 필드도 검증 필요
	@DisplayName("사용자가 관심사 목록을 조회한다.")
	@Test
	void list_interest() {
		given(this.specification).filter(filter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.sessionId(sessionId)
			.when()
			.get(LIST_INTEREST_URL)
			.then()
			.statusCode(OK.value())
			.body("interests", hasSize(3))
			.body("interests.id", hasItems(
				BackendInterestFixture.id(),
				JavaInterestFixture.id(),
				SpringInterestFixture.id()
			));
	}
}
