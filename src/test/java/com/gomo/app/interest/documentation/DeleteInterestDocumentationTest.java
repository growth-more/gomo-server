package com.gomo.app.interest.documentation;

import static io.restassured.RestAssured.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.common.util.LoginMemberHelper;
import com.gomo.app.interest.common.dataprovider.InterestDataProvider;
import com.gomo.app.interest.documentation.snippet.DeleteInterestSnippet;
import com.gomo.app.interest.domain.model.Interest;

public class DeleteInterestDocumentationTest extends DocumentationTestBase {

	private static final String INTEREST_URL = "/interests/{id}";

	private final RestDocumentationFilter filter = DeleteInterestSnippet.create();

	@Autowired
	private LoginMemberHelper loginHelper;

	@Autowired
	private InterestDataProvider interestDataProvider;
	private Interest interest;

	@BeforeEach
	public void setUp() {
		// sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
		interest = interestDataProvider.backend();
	}

	@DisplayName("사용자가 관심사를 삭제한다.")
	@Test
	void delete_interest() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.when()
			.delete(INTEREST_URL, interest.getId().getId())
			.then()
			.statusCode(NO_CONTENT.value());
	}
}
