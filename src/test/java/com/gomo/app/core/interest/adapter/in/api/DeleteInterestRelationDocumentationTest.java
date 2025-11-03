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

import com.gomo.app.core.interest.adapter.in.api.request.CreateInterestRelationRequest;
import com.gomo.app.core.interest.adapter.in.api.request.CreateInterestRequest;
import com.gomo.app.core.interest.adapter.in.api.snippet.DeleteInterestRelationSnippet;
import com.gomo.app.core.interest.domain.repository.InterestRelationRepository;
import com.gomo.app.core.interest.domain.repository.InterestRepository;
import com.gomo.app.test.DocumentationTestBase;

@DisplayName("[Presentation documentation]: 관심사 관계 삭제 테스트")
public class DeleteInterestRelationDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = DeleteInterestRelationSnippet.create();

	@Autowired
	private InterestApi interestApi;

	@Autowired
	private InterestNetworkApi interestNetworkApi;

	@Autowired
	private InterestRepository interestRepository;

	@Autowired
	private InterestRelationRepository interestRelationRepository;

	private UUID interestRelationId;

	@BeforeEach
	public void setUp() {
		UUID depth1Id = createInterest("depth1");
		UUID depth2Id = createInterest("depth2");

		interestRelationId = createInterestRelation(depth1Id, depth2Id);
	}

	@AfterEach
	void tearDown() {
		interestRepository.deleteAllInBatch();
		interestRelationRepository.deleteAllInBatch();
	}

	@DisplayName("사용자가 두 가지 관심사 간의 연결선을 삭제한다.")
	@Test
	void delete_interest_relation() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.when()
			.delete("/interests/networks/relations/{id}", interestRelationId)
			.then()
			.statusCode(NO_CONTENT.value());
	}

	private UUID createInterest(String name) {
		return interestApi.create(super.authInfo, CreateInterestRequest.of(name, "#FF0000", null)).getBody().getId();
	}

	private UUID createInterestRelation(UUID depth1Id, UUID depth2Id) {
		return interestNetworkApi.createRelation(this.authInfo, CreateInterestRelationRequest.of(depth1Id, depth2Id)).getBody().getId();
	}
}
