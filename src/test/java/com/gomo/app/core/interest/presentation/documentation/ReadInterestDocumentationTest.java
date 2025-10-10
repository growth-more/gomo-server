package com.gomo.app.core.interest.presentation.documentation;

import static io.restassured.RestAssured.*;
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

import com.gomo.app.core.interest.domain.repository.InterestRepository;
import com.gomo.app.core.interest.presentation.api.InterestApi;
import com.gomo.app.core.interest.presentation.api.request.CreateInterestRequest;
import com.gomo.app.core.interest.presentation.documentation.snippet.ReadInterestSnippet;
import com.gomo.app.test.DocumentationTestBase;

@DisplayName("[Presentation documentation]: 관심사 단건 조회 테스트")
public class ReadInterestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = ReadInterestSnippet.create();

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

	@DisplayName("사용자가 하나의 관심사(Backend)를 조회한다.")
	@Test
	void read_interest() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.when()
			.get("/interests/{id}", interestId)
			.then()
			.statusCode(OK.value());
	}

	private UUID createInterest(String name) {
		return interestApi.create(super.authInfo, CreateInterestRequest.of(name, "#FF0000", null)).getBody().getId();
	}
}
