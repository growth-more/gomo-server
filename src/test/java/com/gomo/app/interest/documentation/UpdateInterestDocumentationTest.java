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
import com.gomo.app.interest.common.constant.NonExistInterestField;
import com.gomo.app.interest.common.fixture.interest.BackendInterestFixture;
import com.gomo.app.interest.common.util.InterestDBDataHelper;
import com.gomo.app.interest.documentation.snippet.UpdateInterestSnippet;
import com.gomo.app.interest.exception.InterestErrorCode;
import com.gomo.app.interest.presentation.request.UpdateInterestRequest;

public class UpdateInterestDocumentationTest extends DocumentationTestBase {

	private static final String INTEREST_URL = "/interests/{interestId}";
	private final static String INVALID_INTEREST_NAME = "# !";

	private final RestDocumentationFilter filter = UpdateInterestSnippet.create();
	private final RestDocumentationFilter errorFilter = UpdateInterestSnippet.createError();

	@Autowired
	private LoginMemberHelper loginHelper;

	@Autowired
	private InterestDBDataHelper interestDBDataHelper;

	@BeforeEach
	public void setUp() {
		sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
	}

	@AfterEach
	void tearDown() {
		interestDBDataHelper.cleanUp();
	}

	@DisplayName("사용자가 관심사 이름을 수정한다.")
	@Test
	void update_interest() {
		given(this.specification).filter(filter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.body(UpdateInterestRequest.of(NonExistInterestField.NAME))
			.when()
			.put(INTEREST_URL, BackendInterestFixture.id())
			.then()
			.statusCode(NO_CONTENT.value());
	}

	@DisplayName("사용자가 잘못된 이름으로 관심사를 수정한다.")
	@Test
	void update_interest_with_invalid_name() {
		given(this.specification).filter(errorFilter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.body(UpdateInterestRequest.of(INVALID_INTEREST_NAME))
			.when()
			.put(INTEREST_URL, BackendInterestFixture.id())
			.then()
			.statusCode(UNPROCESSABLE_ENTITY.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo("422"))
			.body("code", equalTo(InterestErrorCode.INVALID_PARAMETER.name()))
			.body("message", equalTo("Invalid parameter: " + INVALID_INTEREST_NAME))
			.body("path", equalTo(INTEREST_URL));
	}
}
