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
import com.gomo.app.interest.common.fixture.majorinterest.FirstMajorInterestFixture;
import com.gomo.app.interest.common.fixture.majorinterest.SecondMajorInterestFixture;
import com.gomo.app.interest.documentation.snippet.ListMajorInterestSnippet;

public class ListMajorInterestDocumentationTest extends DocumentationTestBase {

	private static final String LIST_MAJOR_INTEREST_URL = "/interests/majors";

	private final RestDocumentationFilter filter = ListMajorInterestSnippet.create();

	@Autowired
	private LoginMemberHelper loginHelper;

	@BeforeEach
	public void setUp() {
		sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
	}

	// TODO <jhl221123>: id 외 다른 필드도 검증 필요
	@DisplayName("사용자가 주요 관심사 목록을 조회한다.")
	@Test
	void list_major_interest() {
		given(this.specification).filter(filter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.sessionId(sessionId)
			.when()
			.get(LIST_MAJOR_INTEREST_URL)
			.then()
			.statusCode(OK.value())
			.body("majorInterests", hasSize(2))
			.body("majorInterests.id", hasItems(
				FirstMajorInterestFixture.id(),
				SecondMajorInterestFixture.id()
			));
	}
}
