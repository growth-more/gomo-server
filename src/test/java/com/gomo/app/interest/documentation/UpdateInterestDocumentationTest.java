package com.gomo.app.interest.documentation;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.interest.common.util.InterestDataHelper;
import com.gomo.app.interest.documentation.snippet.UpdateInterestSnippet;
import com.gomo.app.interest.presentation.request.UpdateInterestRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import static com.gomo.app.common.exception.DomainErrorCode.INVALID_PARAMETER;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@DisplayName("[Presentation documentation]: 관심사 수정 테스트")
public class UpdateInterestDocumentationTest extends DocumentationTestBase {

	private static final String UPDATED_INTEREST_ID = "3bd1b3f7-d7c6-11ef-abb8-a7e09b2a499c";

	private final RestDocumentationFilter filter = UpdateInterestSnippet.create();
	private final RestDocumentationFilter errorFilter = UpdateInterestSnippet.createError();

	@Autowired
	private InterestDataHelper interestDataHelper;

	@AfterEach
	void tearDown() {
		interestDataHelper.cleanUp();
	}

	@DisplayName("사용자가 관심사 이름을 수정한다.")
	@Test
	void update_interest() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + token)
			.body(UpdateInterestRequest.of("updated interest name"))
			.when()
			.put("/interests/{id}", UPDATED_INTEREST_ID)
			.then()
			.statusCode(NO_CONTENT.value());
	}

	@DisplayName("사용자가 잘못된 이름으로 관심사를 수정한다.")
	@Test
	void update_interest_with_invalid_name() {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
            .header(AUTHORIZATION, "Bearer " + token)
			.body(UpdateInterestRequest.of("forbidden{}"))
			.when()
			.put("/interests/{id}", UPDATED_INTEREST_ID)
			.then()
			.statusCode(UNPROCESSABLE_ENTITY.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo(INVALID_PARAMETER.getHttpStatus()))
			.body("code", equalTo(INVALID_PARAMETER.name()))
			.body("message", equalTo("Interest name cannot contain forbidden characters"))
			.body("path", matchesRegex("/interests/[a-f0-9\\-]{36}"));
	}
}
