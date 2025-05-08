package com.gomo.app.interest.documentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.interest.documentation.snippet.UpdateInterestSnippet;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.exception.code.InterestNameErrorCode;
import com.gomo.app.interest.presentation.InterestApi;
import com.gomo.app.interest.presentation.request.CreateInterestRequest;
import com.gomo.app.interest.presentation.request.UpdateInterestRequest;

@DisplayName("[Presentation documentation]: 관심사 수정 테스트")
public class UpdateInterestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = UpdateInterestSnippet.create();
	private final RestDocumentationFilter errorFilter = UpdateInterestSnippet.createError();

	@Autowired
	private InterestApi interestApi;

	@Autowired
	private InterestRepository interestRepository;

	private UUID interestId;

	@BeforeEach
	public void setUp() {
		interestId = createInterest("interest");
	}

	@AfterEach
	void tearDown() {
		interestRepository.deleteAllInBatch();
	}

	@DisplayName("관심사를 수정한다.")
	@Test
	void update_interest() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.body(UpdateInterestRequest.of("name", "#FF0000"))
			.when()
			.put("/interests/{id}", interestId)
			.then()
			.statusCode(NO_CONTENT.value());
	}

	@DisplayName("금지된 문자가 포함된 관심사 이름으로 수정한다.")
	@Test
	void update_interest_with_forbidden_name() {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
            .header(AUTHORIZATION, "Bearer " + accessToken)
			.body(UpdateInterestRequest.of("forbidden{}", "#FF0000"))
			.when()
			.put("/interests/{id}", interestId)
			.then()
			.statusCode(InterestNameErrorCode.FORBIDDEN.getHttpStatus())
			.body("timestamp", instanceOf(String.class))
			.body("path", matchesRegex("/interests/[a-f0-9\\-]{36}"))
			.body("httpStatus", equalTo(InterestNameErrorCode.FORBIDDEN.getHttpStatus()))
			.body("code", equalTo(InterestNameErrorCode.FORBIDDEN.getErrorCode()))
			.body("message", equalTo(InterestNameErrorCode.FORBIDDEN.getMessage()));
	}

	private UUID createInterest(String name) {
		return interestApi.create(super.authInfo, CreateInterestRequest.of(name, "#FF0000", null)).getBody().getId();
	}
}
