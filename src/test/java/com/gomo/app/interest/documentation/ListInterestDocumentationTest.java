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
import com.gomo.app.interest.documentation.snippet.ListInterestSnippet;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.presentation.InterestApi;
import com.gomo.app.interest.presentation.request.CreateInterestRequest;

@DisplayName("[Presentation documentation]: 관심사 목록 조회 테스트")
public class ListInterestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = ListInterestSnippet.create();

	@Autowired
	private InterestApi interestApi;

	@Autowired
	private InterestRepository interestRepository;

	@BeforeEach
	public void setUp() {
		createInterest("depth1");
		createInterest("depth2");
		createInterest("depth3");
	}

	@AfterEach
	void tearDown() {
		interestRepository.deleteAllInBatch();
	}

	@DisplayName("사용자가 관심사 목록을 조회한다.")
	@Test
	void list_interest() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.when()
			.get("/interests")
			.then()
			.statusCode(OK.value())
			.body("interests", hasSize(3));
	}

	private void createInterest(String name) {
		interestApi.create(super.authInfo, CreateInterestRequest.of(name, "#FF0000", null));
	}
}
