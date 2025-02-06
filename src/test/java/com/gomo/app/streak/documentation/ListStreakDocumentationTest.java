package com.gomo.app.streak.documentation;

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
import com.gomo.app.common.util.LoginMemberHelper;
import com.gomo.app.streak.common.dataprovider.StreakDataProvider;
import com.gomo.app.streak.documentation.snippet.ListStreakSnippet;
import com.gomo.app.streak.domain.model.Streak;
import com.gomo.app.streak.domain.model.StreakType;

public class ListStreakDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = ListStreakSnippet.create();

	@Autowired
	private LoginMemberHelper loginHelper;

	@Autowired
	StreakDataProvider streakDataProvider;
	Streak dailyFirstStreak;
	Streak dailySecondStreak;
	Streak weeklyStreak;

	@BeforeEach
	public void setUp() {
		// sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
		dailyFirstStreak = streakDataProvider.dailyFirstStreak();
		dailySecondStreak = streakDataProvider.dailySecondStreak();
		weeklyStreak = streakDataProvider.weeklyStreak();
	}

	@DisplayName("사용자가 스트릭 목록을 조회한다.")
	@Test
	void list_streak() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.param("startDate", dailyFirstStreak.getFilledDate().toString())
			.param("endDate", dailySecondStreak.getFilledDate().toString())
			.when()
			.get("/streaks")
			.then()
			.statusCode(OK.value())
			.body("dailyStreaks", hasSize(2))
			.body("dailyStreaks.id", hasItems(
				dailyFirstStreak.getId().toString(),
				dailySecondStreak.getId().toString()
			))
			.body("dailyStreaks.streakType", everyItem(equalTo(StreakType.DAILY.name())))
			.body("dailyStreaks.filledDate", hasItems(
				dailyFirstStreak.getFilledDate().toString(),
				dailySecondStreak.getFilledDate().toString()
			))
			.body("dailyStreaks.completedQuestCount", hasItems(
				dailyFirstStreak.getCompletedQuestCount(),
				dailySecondStreak.getCompletedQuestCount()
			))
			.body("weeklyStreaks", hasSize(1))
			.body("weeklyStreaks.id", hasItems(weeklyStreak.getId().toString()))
			.body("weeklyStreaks.streakType", everyItem(equalTo(StreakType.WEEKLY.name())))
			.body("weeklyStreaks.filledDate", hasItems(weeklyStreak.getFilledDate().toString()))
			.body("weeklyStreaks.completedQuestCount", hasItems(weeklyStreak.getCompletedQuestCount()))
			.body("monthlyStreaks", hasSize(0));
	}
}
