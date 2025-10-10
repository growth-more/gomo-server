package com.gomo.app.core.interest.presentation.documentation;

import static io.restassured.RestAssured.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import java.util.List;
import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.displayorder.UpdatedOrderDto;
import com.gomo.app.core.interest.domain.repository.InterestRepository;
import com.gomo.app.core.interest.domain.repository.MajorInterestRepository;
import com.gomo.app.core.interest.presentation.api.InterestApi;
import com.gomo.app.core.interest.presentation.api.MajorInterestApi;
import com.gomo.app.core.interest.presentation.api.request.CreateInterestRequest;
import com.gomo.app.core.interest.presentation.api.request.OrderUpdateMajorInterestRequest;
import com.gomo.app.core.interest.presentation.documentation.snippet.OrderUpdateMajorInterestSnippet;
import com.gomo.app.test.DocumentationTestBase;

@DisplayName("[Presentation documentation]: 주요 관심사 정렬 순서 변경 테스트")
public class OrderUpdateMajorInterestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = OrderUpdateMajorInterestSnippet.create();

	@Autowired
	private InterestApi interestApi;

	@Autowired
	private MajorInterestApi majorInterestApi;

	@Autowired
	private InterestRepository interestRepository;

	@Autowired
	private MajorInterestRepository majorInterestRepository;

	private UUID majorInterest1Id;
	private UUID majorInterest2Id;

	@BeforeEach
	public void setUp() {
		UUID interest1Id = createInterest("interest1");
		UUID interest2Id = createInterest("interest2");
		majorInterest1Id = createMajorInterest(interest1Id);
		majorInterest2Id = createMajorInterest(interest2Id);
	}

	@AfterEach
	void tearDown() {
		majorInterestRepository.deleteAllInBatch();
		interestRepository.deleteAllInBatch();
	}

	@DisplayName("사용자가 주요 관심사의 정렬 순서를 변경한다.")
	@Test
	void update_major_interest_order() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.body(getRequest())
			.when()
			.put("/interests/majors/orders")
			.then()
			.statusCode(NO_CONTENT.value());
	}

	private @NotNull OrderUpdateMajorInterestRequest getRequest() {
		return OrderUpdateMajorInterestRequest.of(
			List.of(
				UpdatedOrderDto.of(majorInterest1Id, 2),
				UpdatedOrderDto.of(majorInterest2Id, 1)
			)
		);
	}

	private UUID createMajorInterest(UUID interestId) {
		return majorInterestApi.create(this.authInfo, interestId).getBody().getId();
	}

	private UUID createInterest(String name) {
		return interestApi.create(super.authInfo, CreateInterestRequest.of(name, "#FF0000", null)).getBody().getId();
	}
}
