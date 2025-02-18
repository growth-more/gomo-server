package com.gomo.app.interest.documentation;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.interest.common.dataprovider.InterestDataProvider;
import com.gomo.app.interest.documentation.snippet.ReadInterestSnippet;
import com.gomo.app.interest.domain.model.Interest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@DisplayName("[Presentation documentation]: 관심사 단건 조회 테스트")
public class ReadInterestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = ReadInterestSnippet.create();

	@Autowired
	private InterestDataProvider interestDataProvider;
	private Interest interest;

	@BeforeEach
	public void setUp() {
		interest = interestDataProvider.backend();
	}

	@DisplayName("사용자가 하나의 관심사(Backend)를 조회한다.")
	@Test
	void read_interest() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + token)
			.when()
			.get("/interests/{id}", interest.getId().getId())
			.then()
			.statusCode(OK.value())
			.body("id", equalTo(interest.getId().toString()))
			.body("registrantId", equalTo(interest.getRegistrantId().toString()))
			.body("name", equalTo(interest.getName().toString()))
			.body("logoUrl", equalTo(interest.getLogoUrl()))
			.body("level", equalTo(interest.getProficiency().getLevel().getLevel()))
			.body("score", equalTo(interest.getProficiency().getScore().getScore()))
			.body("totalScore", equalTo(interest.getProficiency().getTotalScore()));
	}
}
