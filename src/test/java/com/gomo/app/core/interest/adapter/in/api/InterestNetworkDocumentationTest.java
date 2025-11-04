package com.gomo.app.core.interest.adapter.in.api;

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

import com.gomo.app.core.interest.adapter.in.api.request.CreateInterestRelationRequest;
import com.gomo.app.core.interest.adapter.in.api.request.CreateInterestRequest;
import com.gomo.app.core.interest.adapter.in.api.snippet.InterestNetworkSnippet;
import com.gomo.app.core.interest.domain.repository.InterestRelationRepository;
import com.gomo.app.core.interest.domain.repository.InterestRepository;
import com.gomo.app.test.DocumentationTestBase;

@DisplayName("[Presentation documentation]: 관심사 네트워크 조회 테스트")
public class InterestNetworkDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = InterestNetworkSnippet.create();

	@Autowired
	private InterestApi interestApi;

	@Autowired
	private InterestNetworkApi interestNetworkApi;

	@Autowired
	private InterestRepository interestRepository;

	@Autowired
	private InterestRelationRepository interestRelationRepository;

	@BeforeEach
	public void setUp() {
		UUID depth1Id = createInterest("depth1");
		UUID depth2Id = createInterest("depth2");
		createInterest("depth3");
		createInterestRelation(depth1Id, depth2Id);
	}

	@AfterEach
	void tearDown() {
		interestRelationRepository.deleteAllInBatch();
		interestRepository.deleteAllInBatch();
	}

	@DisplayName("사용자가 관심사 네트워크를 조회한다.")
	@Test
	void read_interest_network() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.when()
			.get("/interests/networks")
			.then()
			.statusCode(OK.value())
			.body("interests", hasSize(3))
			.body("relations", hasSize(1));
	}

	private UUID createInterest(String name) {
		return interestApi.create(super.sessionInfo, CreateInterestRequest.of(name, "#FF0000", null)).getBody().getId();
	}

	private void createInterestRelation(UUID depth1Id, UUID depth2Id) {
		interestNetworkApi.createRelation(this.sessionInfo, CreateInterestRelationRequest.of(depth1Id, depth2Id));
	}
}
