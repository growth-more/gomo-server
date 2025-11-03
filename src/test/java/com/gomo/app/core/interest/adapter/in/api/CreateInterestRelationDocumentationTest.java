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
import com.gomo.app.core.interest.adapter.in.api.snippet.CreateInterestRelationSnippet;
import com.gomo.app.core.interest.domain.repository.InterestRelationRepository;
import com.gomo.app.core.interest.domain.repository.InterestRepository;
import com.gomo.app.test.DocumentationTestBase;

@DisplayName("[Presentation documentation]: 관심사 관계 생성 테스트")
public class CreateInterestRelationDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = CreateInterestRelationSnippet.create();

	@Autowired
	private InterestApi interestApi;

	@Autowired
	private InterestRepository interestRepository;

	@Autowired
	private InterestRelationRepository interestRelationRepository;

	private UUID depth1Id;
	private UUID depth2Id;

	@BeforeEach
	public void setUp() {
		depth1Id = createInterest("depth1");
		depth2Id = createInterest("depth2");
	}

	@AfterEach
	void tearDown() {
		interestRelationRepository.deleteAllInBatch();
		interestRepository.deleteAllInBatch();
	}

	@DisplayName("사용자가 관심사 간의 계층 구조를 추가한다.")
	@Test
	void create_interest_relation() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.body(CreateInterestRelationRequest.of(depth1Id, depth2Id))
			.when()
			.post("/interests/networks/relations")
			.then()
			.statusCode(CREATED.value())
			.body("id", hasLength(36));
	}

	private UUID createInterest(String name) {
		return interestApi.create(super.authInfo, CreateInterestRequest.of(name, "#FF0000", null)).getBody().getId();
	}
}
