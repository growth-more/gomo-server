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
import com.gomo.app.core.interest.adapter.in.api.snippet.DeleteMajorInterestSnippet;
import com.gomo.app.core.interest.domain.repository.InterestRepository;
import com.gomo.app.core.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.test.DocumentationTestBase;

@DisplayName("[Presentation documentation]: 주요 관심사 삭제 테스트")
public class DeleteMajorInterestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = DeleteMajorInterestSnippet.create();

	@Autowired
	private InterestApi interestApi;

	@Autowired
	private MajorInterestApi majorInterestApi;

	@Autowired
	private InterestRepository interestRepository;

	@Autowired
	private MajorInterestRepository majorInterestRepository;

	private UUID majorInterestId;

	@BeforeEach
	public void setUp() {
		UUID interestId = createInterest("interest");
		majorInterestId = createMajorInterest(interestId);
	}

	@AfterEach
	void tearDown() {
		interestRepository.deleteAllInBatch();
		majorInterestRepository.deleteAllInBatch();
	}

	@DisplayName("사용자가 주요 관심사를 삭제한다.")
	@Test
	void delete_major_interest() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.when()
			.delete("/interests/majors/{id}", majorInterestId)
			.then()
			.statusCode(NO_CONTENT.value());
	}

	private UUID createMajorInterest(UUID interestId) {
		return majorInterestApi.create(this.authInfo, interestId).getBody().getId();
	}

	private UUID createInterest(String name) {
		return interestApi.create(super.authInfo, CreateInterestRequest.of(name, "#FF0000", null)).getBody().getId();
	}
}
