package com.gomo.app.streak.documentation;

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
import com.gomo.app.streak.documentation.snippet.ReadAchieverSnippet;
import com.gomo.app.core.streak.domain.model.Achiever;
import com.gomo.app.core.streak.domain.repository.AchieverRepository;
import com.gomo.app.streak.fixture.AchieverFixture;

@DisplayName("[Presentation documentation]: 성취자 조회 테스트")
public class ReadAchieverDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = ReadAchieverSnippet.create();

	@Autowired
	AchieverRepository achieverRepository;

	@BeforeEach
	public void setUp() {
		Achiever achiever = AchieverFixture.achiever(sessionMemberId);
		achieverRepository.save(achiever);
	}

	@AfterEach
	void tearDown() {
		achieverRepository.deleteAllInBatch();
	}

	@DisplayName("사용자가 스트릭 목록을 조회한다.")
	@Test
	void list_streak() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.when()
			.get("/achievers")
			.then()
			.statusCode(OK.value())
			.body("currentStreakDays", equalTo(0))
			.body("longestStreakDays", equalTo(0));
	}
}
