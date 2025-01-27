package com.gomo.app.interest.documentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.common.exception.DomainErrorCode;
import com.gomo.app.common.util.LoginMemberHelper;
import com.gomo.app.interest.common.dataprovider.InterestDataProvider;
import com.gomo.app.interest.common.util.InterestDataHelper;
import com.gomo.app.interest.documentation.snippet.UpdateInterestSnippet;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.presentation.request.UpdateInterestRequest;

public class UpdateInterestDocumentationTest extends DocumentationTestBase {

	private static final String INTEREST_URL = "/interests/{id}";

	private final RestDocumentationFilter filter = UpdateInterestSnippet.create();
	private final RestDocumentationFilter errorFilter = UpdateInterestSnippet.createError();

	@Autowired
	private LoginMemberHelper loginHelper;

	@Autowired
	private InterestDataHelper interestDataHelper;

	@Autowired
	private InterestDataProvider interestDataProvider;
	private Interest interest;

	@BeforeEach
	public void setUp() {
		// sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
		interest = interestDataProvider.backend();
	}

	@AfterEach
	void tearDown() {
		interestDataHelper.cleanUp();
	}

	@DisplayName("사용자가 관심사 이름을 수정한다.")
	@Test
	void update_interest() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.body(UpdateInterestRequest.of("updated interest name"))
			.when()
			.put(INTEREST_URL, interest.getId().getId())
			.then()
			.statusCode(NO_CONTENT.value());
	}

	@DisplayName("사용자가 잘못된 이름으로 관심사를 수정한다.")
	@Test
	void update_interest_with_invalid_name() {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.body(UpdateInterestRequest.of("forbidden #{}!"))
			.when()
			.put(INTEREST_URL, interest.getId())
			.then()
			.statusCode(UNPROCESSABLE_ENTITY.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo("422"))
			.body("code", equalTo(DomainErrorCode.INVALID_PARAMETER.name()))
			.body("message", equalTo("Invalid parameter: forbidden #{}!"))
			.body("path", equalTo(INTEREST_URL));
	}
}
