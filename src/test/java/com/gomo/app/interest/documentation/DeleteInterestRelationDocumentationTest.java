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
import com.gomo.app.interest.common.fixture.interestrelation.SecondToParentInterestRelationFixture;
import com.gomo.app.interest.common.util.InterestRelationDBDataHelper;
import com.gomo.app.interest.documentation.snippet.DeleteInterestRelationSnippet;

public class DeleteInterestRelationDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = DeleteInterestRelationSnippet.create();

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

	@DisplayName("사용자가 두 가지 관심사 간의 연결선을 삭제한다.")
	@Test
	void delete_interest_relation() {
		given(this.specification).filter(filter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.sessionId(sessionId)
			.when()
			.delete("/interests/networks/relations/{id}", SecondToParentInterestRelationFixture.id())
			.then()
			.statusCode(NO_CONTENT.value());
	}
}
