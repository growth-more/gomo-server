package com.gomo.app.core.interest.adapter.in.api;

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

import com.gomo.app.core.interest.adapter.in.api.request.CreateInterestRequest;
import com.gomo.app.core.interest.adapter.in.api.snippet.DeleteInterestSnippet;
import com.gomo.app.core.interest.domain.repository.InterestRepository;
import com.gomo.app.test.DocumentationTestBase;

@DisplayName("[Presentation documentation]: 관심사 삭제 테스트")
public class DeleteInterestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = DeleteInterestSnippet.create();

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

	@DisplayName("사용자가 관심사를 삭제한다.")
	@Test
	void delete_interest() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.when()
			.delete("/interests/{id}", interestId)
			.then()
			.statusCode(NO_CONTENT.value());
	}

	private UUID createInterest(String name) {
		return interestApi.create(super.authInfo, CreateInterestRequest.of(name, "#FF0000", null)).getBody().getId();
	}
}
