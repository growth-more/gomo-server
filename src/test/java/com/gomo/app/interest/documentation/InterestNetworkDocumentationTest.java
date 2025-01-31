package com.gomo.app.interest.documentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.common.fixture.TestMemberFixture;
import com.gomo.app.common.util.LoginMemberHelper;
import com.gomo.app.interest.common.dataprovider.InterestDataProvider;
import com.gomo.app.interest.common.fixture.interestrelation.FirstToParentInterestRelationFixture;
import com.gomo.app.interest.common.fixture.interestrelation.SecondToParentInterestRelationFixture;
import com.gomo.app.interest.documentation.snippet.InterestNetworkSnippet;
import com.gomo.app.interest.domain.model.Interest;

public class InterestNetworkDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = InterestNetworkSnippet.create();

	@Autowired
	private LoginMemberHelper loginHelper;

	@Autowired
	private InterestDataProvider interestDataProvider;
	private Interest backend;
	private Interest spring;
	private Interest java;

	@BeforeEach
	public void setUp() {
		sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
		backend = interestDataProvider.backend();
		spring = interestDataProvider.spring();
		java = interestDataProvider.java();
	}

	// TODO <jhl221123>: id 외 다른 필드도 검증 필요
	@DisplayName("사용자가 관심사 그래프를 조회한다.")
	@Test
	void read_interest_network() {
		given(this.specification).filter(filter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.sessionId(sessionId)
			.when()
			.get("/interests/networks")
			.then()
			.statusCode(OK.value())
			.body("interests", hasSize(3))
			.body("interests.id", hasItems(backend.getId(), spring.getId(), java.getId()))
			.body("relations", hasSize(2))
			.body("relations.id", hasItems(
				SecondToParentInterestRelationFixture.id(),
				FirstToParentInterestRelationFixture.id()
			));
	}
}
