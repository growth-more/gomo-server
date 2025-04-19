package com.gomo.app.interest.documentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.interest.common.dataprovider.InterestDataProvider;
import com.gomo.app.interest.common.dataprovider.InterestRelationDataProvider;
import com.gomo.app.interest.documentation.snippet.InterestNetworkSnippet;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.interest.domain.model.InterestRelation;

@DisplayName("[Presentation documentation]: 관심사 네트워크 조회 테스트")
public class InterestNetworkDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = InterestNetworkSnippet.create();

	@Autowired
	private InterestDataProvider interestDataProvider;
	private Interest backend;
	private Interest java;
	private Interest spring;

	@Autowired
	private InterestRelationDataProvider interestRelationDataProvider;
	private InterestRelation toJava;

	@BeforeEach
	public void setUp() {
		backend = interestDataProvider.backend();
		java = interestDataProvider.java();
		spring = interestDataProvider.spring();
		toJava = interestRelationDataProvider.backendToJava();
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
			.body("interests.id", hasItems(
				backend.getId().toString(),
				spring.getId().toString(),
				java.getId().toString()
			))
			.body("interests.registrantId", everyItem(equalTo(backend.getRegistrantId().toString())))
			.body("interests.name", hasItems(
				backend.getName().toString(),
				spring.getName().toString(),
				java.getName().toString()
			))
			.body("interests.logoUrl", hasItems(
				backend.getLogo().getUrl(),
				spring.getLogo().getUrl(),
				java.getLogo().getUrl()
			))
			.body("interests.level", hasItems(
				backend.getProficiency().getLevel().getLevel(),
				spring.getProficiency().getLevel().getLevel(),
				java.getProficiency().getLevel().getLevel()
			))
			.body("interests.score", hasItems(
				backend.getProficiency().getScore().getScore(),
				spring.getProficiency().getScore().getScore(),
				java.getProficiency().getScore().getScore()
			))
			.body("interests.totalScore", hasItems(
				backend.getProficiency().getTotalScore(),
				spring.getProficiency().getTotalScore(),
				java.getProficiency().getTotalScore()
			))
			.body("relations", hasSize(1))
			.body("relations.id", hasItems(toJava.getId().toString()))
			.body("relations.registrantId", everyItem(equalTo(backend.getRegistrantId().toString())))
			.body("relations.parentInterestId", hasItems(toJava.getParentInterestId().toString()))
			.body("relations.childInterestId", hasItems(toJava.getChildInterestId().toString()));
	}
}
