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
import com.gomo.app.common.util.LoginMemberHelper;
import com.gomo.app.interest.common.dataprovider.InterestDataProvider;
import com.gomo.app.interest.common.util.InterestRelationDataHelper;
import com.gomo.app.interest.documentation.snippet.CreateInterestRelationSnippet;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.presentation.request.CreateInterestRelationRequest;

@DisplayName("[Presentation documentation]: 관심사 관계 생성 테스트")
public class CreateInterestRelationDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = CreateInterestRelationSnippet.create();

	@Autowired
	private LoginMemberHelper loginHelper;

	@Autowired
	private InterestRelationDataHelper interestRelationDataHelper;

	@Autowired
	private InterestDataProvider interestDataProvider;
	private Interest backend;
	private Interest spring;

	@BeforeEach
	public void setUp() {
		// sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
		backend = interestDataProvider.backend();
		spring = interestDataProvider.spring();
	}

	@AfterEach
	public void tearDown() {
		interestRelationDataHelper.cleanUp();
	}

	@DisplayName("사용자가 관심사 간의 계층 구조를 추가한다.")
	@Test
	void create_interest_relation() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.body(CreateInterestRelationRequest.of(backend.getRegistrantId().getId(), backend.getId().getId(), spring.getId().getId()))
			.when()
			.post("/interests/networks/relations")
			.then()
			.statusCode(CREATED.value())
			.body("id", hasLength(36));
	}
}
