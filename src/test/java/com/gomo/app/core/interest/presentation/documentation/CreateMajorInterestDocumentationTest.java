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
import com.gomo.app.core.interest.exception.code.MajorInterestErrorCode;
import com.gomo.app.core.interest.presentation.api.InterestApi;
import com.gomo.app.core.interest.presentation.api.MajorInterestApi;
import com.gomo.app.core.interest.presentation.api.request.CreateInterestRequest;
import com.gomo.app.core.interest.presentation.documentation.snippet.CreateMajorInterestSnippet;
import com.gomo.app.test.DocumentationTestBase;

@DisplayName("[Presentation documentation]: 주요 관심사 생성 테스트")
public class CreateMajorInterestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = CreateMajorInterestSnippet.create();
	private final RestDocumentationFilter errorFilter = CreateMajorInterestSnippet.createError();

	@Autowired
	private InterestApi interestApi;

	@Autowired
	private MajorInterestApi majorInterestApi;

	@Autowired
	private InterestRepository interestRepository;

	@Autowired
	private MajorInterestRepository majorInterestRepository;

	private UUID interestId;

	@BeforeEach
	public void setUp() {
		interestId = createInterest("interest");
	}

	@AfterEach
	void tearDown() {
		majorInterestRepository.deleteAllInBatch();
		interestRepository.deleteAllInBatch();
	}

	@DisplayName("주요 관심사를 등록한다.")
	@Test
	void create_major_interest() {
		given(this.specification)
			.filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.when()
			.post("/interests/{id}/majors", interestId)
			.then()
			.statusCode(CREATED.value())
			.body("id", hasLength(36));
	}

	@DisplayName("이미 등록된 주요 관심사를 중복 등록한다.")
	@Test
	void create_already_major_interest() {
		majorInterestApi.create(super.authInfo, interestId);

		given(this.specification)
			.filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.when()
			.post("/interests/{id}/majors", interestId)
			.then()
			.statusCode(MajorInterestErrorCode.DUPLICATED.getHttpStatus())
			.body("timestamp", instanceOf(String.class))
			.body("path", equalTo("/interests/" + interestId.toString() + "/majors"))
			.body("httpStatus", equalTo(MajorInterestErrorCode.DUPLICATED.getHttpStatus()))
			.body("code", equalTo(MajorInterestErrorCode.DUPLICATED.getErrorCode()))
			.body("message", equalTo(MajorInterestErrorCode.DUPLICATED.getMessage()));
	}

	private UUID createInterest(String name) {
		return interestApi.create(super.authInfo, CreateInterestRequest.of(name, "#FF0000", null)).getBody().getId();
	}
}
