package com.gomo.app.core.interest.presentation.documentation;

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

import com.gomo.app.core.interest.domain.repository.InterestRepository;
import com.gomo.app.core.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.core.interest.presentation.api.InterestApi;
import com.gomo.app.core.interest.presentation.api.MajorInterestApi;
import com.gomo.app.core.interest.presentation.api.request.CreateInterestRequest;
import com.gomo.app.core.interest.presentation.documentation.snippet.ListMajorInterestSnippet;
import com.gomo.app.test.DocumentationTestBase;

@DisplayName("[Presentation documentation]: 주요 관심사 목록 조회 테스트")
public class ListMajorInterestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = ListMajorInterestSnippet.create();

	@Autowired
	private InterestApi interestApi;

	@Autowired
	private MajorInterestApi majorInterestApi;

	@Autowired
	private InterestRepository interestRepository;

	@Autowired
	private MajorInterestRepository majorInterestRepository;

	@BeforeEach
	public void setUp() {
		UUID interest1Id = createInterest("interest1");
		UUID interest2Id = createInterest("interest2");
		createMajorInterest(interest1Id);
		createMajorInterest(interest2Id);
	}

	@AfterEach
	void tearDown() {
		majorInterestRepository.deleteAllInBatch();
		interestRepository.deleteAllInBatch();
	}

	@DisplayName("사용자가 주요 관심사 목록을 조회한다.")
	@Test
	void list_major_interest() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.when()
			.get("/interests/majors")
			.then()
			.statusCode(OK.value())
			.body("majorInterests", hasSize(2));
	}

	private void createMajorInterest(UUID interestId) {
		majorInterestApi.create(this.authInfo, interestId);
	}

	private UUID createInterest(String name) {
		return interestApi.create(super.authInfo, CreateInterestRequest.of(name, "#FF0000", null)).getBody().getId();
	}
}
