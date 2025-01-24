package com.gomo.app.streak.documentation;

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
import com.gomo.app.streak.common.fixture.StreakFixture;
import com.gomo.app.streak.documentation.snippet.ListStreakSnippet;
import com.gomo.app.streak.domain.model.StreakType;

public class ListStreakDocumentationTest extends DocumentationTestBase {

	private static final String STREAK_URL = "/streaks";

	private final RestDocumentationFilter filter = ListStreakSnippet.create();

	@Autowired
	private LoginMemberHelper loginHelper;

	@BeforeEach
	public void setUp() {
		sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
	}

	@DisplayName("사용자가 스트릭 목록을 조회한다.")
	@Test
	void list_streak() {
		given(this.specification).filter(filter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.param("streakType", StreakType.DAILY.name())
			.when()
			.get(STREAK_URL)
			.then()
			.statusCode(OK.value())
			.body("streakType", is(StreakType.DAILY.name()))
			.body("streaks", hasSize(2))
			.body("streaks.id", hasItems(
				StreakFixture.streak1Id(),
				StreakFixture.streak2Id()
			));
	}
}
