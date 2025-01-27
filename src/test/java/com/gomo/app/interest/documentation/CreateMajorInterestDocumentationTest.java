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
import com.gomo.app.interest.common.dataprovider.InterestDataProvider;
import com.gomo.app.interest.common.util.MajorInterestDBDataHelper;
import com.gomo.app.interest.documentation.snippet.CreateMajorInterestSnippet;
import com.gomo.app.interest.domain.model.Interest;

public class CreateMajorInterestDocumentationTest extends DocumentationTestBase {

	private static final String CREATE_MAJOR_INTEREST_URL = "/interests/{id}/majors";

	private final RestDocumentationFilter filter = CreateMajorInterestSnippet.create();

	@Autowired
	private LoginMemberHelper loginHelper;

	@Autowired
	private MajorInterestDBDataHelper majorInterestDBDataHelper;

	@Autowired
	private InterestDataProvider interestDataProvider;
	private Interest interest;

	@BeforeEach
	public void setUp() {
		sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
		interest = interestDataProvider.backend();
	}

	@AfterEach
	void tearDown() {
		majorInterestDBDataHelper.cleanUp();
	}

	@DisplayName("사용자가 주요 관심사를 등록한다.")
	@Test
	void create_major_interest() {
		given(this.specification).filter(filter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.when()
			.post(CREATE_MAJOR_INTEREST_URL, interest.getId())
			.then()
			.statusCode(CREATED.value())
			.body("id", hasLength(36));
	}
}
