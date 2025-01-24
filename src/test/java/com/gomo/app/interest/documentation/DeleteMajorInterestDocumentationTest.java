package com.gomo.app.interest.documentation;

import static io.restassured.RestAssured.*;
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
import com.gomo.app.interest.common.fixture.interest.SpringInterestFixture;
import com.gomo.app.interest.common.util.MajorInterestDBDataHelper;
import com.gomo.app.interest.documentation.snippet.DeleteMajorInterestSnippet;

public class DeleteMajorInterestDocumentationTest extends DocumentationTestBase {

	private static final String DELETE_MAJOR_INTEREST_URL = "/interests/majors/{id}";

	private final RestDocumentationFilter filter = DeleteMajorInterestSnippet.create();

	@Autowired
	private LoginMemberHelper loginHelper;

	@Autowired
	private MajorInterestDBDataHelper majorInterestDBDataHelper;

	@BeforeEach
	public void setUp() {
		sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
	}

	@AfterEach
	void tearDown() {
		majorInterestDBDataHelper.cleanUp();
	}

	@DisplayName("사용자가 주요 관심사를 삭제한다.")
	@Test
	void delete_major_interest() {
		given(this.specification).filter(filter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.when()
			.delete(DELETE_MAJOR_INTEREST_URL, SpringInterestFixture.id())
			.then()
			.statusCode(NO_CONTENT.value());
	}
}
