package com.gomo.app.core.streak.presentation.documentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.core.streak.domain.model.Streak;
import com.gomo.app.core.streak.domain.model.StreakType;
import com.gomo.app.core.streak.domain.repository.StreakRepository;
import com.gomo.app.core.streak.fixture.StreakFixture;
import com.gomo.app.core.streak.presentation.documentation.snippet.ListStreakSnippet;

@DisplayName("[Presentation documentation]: 스트릭 목록 조회 테스트")
public class ListStreakDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = ListStreakSnippet.create();

	@Autowired
	StreakRepository streakRepository;
	Streak dailyStreak1;
	Streak dailyStreak2;
	Streak weeklyStreak;

	@BeforeEach
	public void setUp() {
		dailyStreak1 = StreakFixture.streak(sessionMemberId, StreakType.DAILY, LocalDate.of(2025, 1, 18));
		dailyStreak2 = StreakFixture.streak(sessionMemberId, StreakType.DAILY, LocalDate.of(2025, 2, 6));
		weeklyStreak = StreakFixture.streak(sessionMemberId, StreakType.WEEKLY, LocalDate.of(2025, 1, 20));
		streakRepository.saveAll(List.of(dailyStreak1, dailyStreak2, weeklyStreak));
	}

	@AfterEach
	void tearDown() {
		streakRepository.deleteAllInBatch();
	}

	@DisplayName("사용자가 스트릭 목록을 조회한다.")
	@Test
	void list_streak() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.param("startDate", dailyStreak1.getFilledDate().toString())
			.param("endDate", dailyStreak2.getFilledDate().toString())
			.when()
			.get("/streaks")
			.then()
			.statusCode(OK.value())
			.body("dailyStreaks", hasSize(2))
			.body("weeklyStreaks", hasSize(1))
			.body("monthlyStreaks", hasSize(0));
	}
}
