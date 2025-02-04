package com.gomo.app.quest.documentation;

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
import com.gomo.app.quest.common.dataprovider.RepeatQuestDataProvider;
import com.gomo.app.quest.documentation.snippet.ListRepeatQuestSnippet;
import com.gomo.app.quest.domain.model.RepeatQuest;

@DisplayName("[Presentation documentation]: 반복 퀘스트 조회 테스트")
public class ListRepeatQuestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = ListRepeatQuestSnippet.create();

	@Autowired
	private LoginMemberHelper loginHelper;

	@Autowired
	private RepeatQuestDataProvider repeatQuestDataProvider;
	private RepeatQuest firstOrderDailyRepeatQuest;
	private RepeatQuest secondOrderDailyRepeatQuest;

	@BeforeEach
	public void setUp() {
		// sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
		firstOrderDailyRepeatQuest = repeatQuestDataProvider.firstOrderDaily();
		secondOrderDailyRepeatQuest = repeatQuestDataProvider.secondOrderDaily();
	}

	@DisplayName("사용자가 반복 퀘스트 목록을 조회한다.")
	@Test
	void list_assign_quest() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.param("questType")
			.when()
			.get("/quests/repeats")
			.then()
			.statusCode(OK.value())
			.body("dailyQuests", hasSize(2))
			.body("dailyQuests.id", containsInAnyOrder(
				firstOrderDailyRepeatQuest.getId().toString(),
				secondOrderDailyRepeatQuest.getId().toString()
			))
			.body("dailyQuests.subjectId", containsInAnyOrder(
				firstOrderDailyRepeatQuest.getQuest().getSubjectId().toString(),
				secondOrderDailyRepeatQuest.getQuest().getSubjectId().toString()
			))
			.body("dailyQuests.questType", everyItem(equalTo(firstOrderDailyRepeatQuest.getQuest().getType().name())))
			.body("dailyQuests.point", everyItem(equalTo(10)))
			.body("dailyQuests.score", everyItem(equalTo(2)))
			.body("dailyQuests.subjectName", containsInAnyOrder(
				firstOrderDailyRepeatQuest.getQuest().getSubjectName().toString(),
				secondOrderDailyRepeatQuest.getQuest().getSubjectName().toString()
			))
			.body("dailyQuests.content", containsInAnyOrder(
				firstOrderDailyRepeatQuest.getQuest().getContent().toString(),
				secondOrderDailyRepeatQuest.getQuest().getContent().toString()
			))
			.body("dailyQuests.displayOrder", containsInAnyOrder(
				firstOrderDailyRepeatQuest.getDisplayOrder().getDisplayOrder(),
				secondOrderDailyRepeatQuest.getDisplayOrder().getDisplayOrder()
			))
			.body("weeklyQuests", hasSize(0))
			.body("monthlyQuests", hasSize(0));
	}
}
