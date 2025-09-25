package com.gomo.app.core.quest.documentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.core.quest.documentation.snippet.CalendarAssignQuestSnippet;
import com.gomo.app.core.quest.domain.model.AssignQuest;
import com.gomo.app.core.quest.domain.model.QuestType;
import com.gomo.app.core.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.core.quest.fixture.AssignQuestFixture;

@DisplayName("[Presentation documentation]: 할당 퀘스트 캘린더 조회 테스트")
public class CalendarAssignQuestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = CalendarAssignQuestSnippet.create();

	@Autowired
	private AssignQuestRepository assignQuestRepository;

	@AfterEach
	void tearDown() {
		assignQuestRepository.deleteAllInBatch();
	}

	@DisplayName("지정된 기간 내 완료하지 못한 퀘스트 이력을 조회한다.")
	@Test
	void calendar_not_completed_quest() {
		LocalDateTime startDateTime = LocalDateTime.of(2025, 7, 16, 10, 0, 0);
		AssignQuest dailyQuest = AssignQuestFixture.assignQuest(sessionMemberId, QuestType.DAILY, startDateTime);
		AssignQuest weeklyQuest = AssignQuestFixture.assignQuest(sessionMemberId, QuestType.WEEKLY, startDateTime);
		AssignQuest monthlyQuest = AssignQuestFixture.assignQuest(sessionMemberId, QuestType.MONTHLY, startDateTime);
		assignQuestRepository.saveAll(List.of(dailyQuest, weeklyQuest, monthlyQuest));

		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.param("isCompleted", false)
			.param("startDateTime", LocalDateTime.of(2025, 7, 16, 0, 0, 0).toString())
			.param("endDateTime", LocalDateTime.of(2025, 7, 16, 23, 59, 59).toString())
			.when()
			.get("/quests/assigns/calendars")
			.then()
			.statusCode(OK.value())
			.body("assignQuests", hasSize(3));
	}

	@DisplayName("지정된 기간 내 완료한 퀘스트 이력을 조회한다.")
	@Test
	void calendar_completed_quest() {
		LocalDateTime completedDateTime = LocalDateTime.of(2025, 7, 16, 10, 0, 0);
		AssignQuest dailyQuest = AssignQuestFixture.assignQuest(sessionMemberId, QuestType.DAILY, true, completedDateTime);
		AssignQuest weeklyQuest = AssignQuestFixture.assignQuest(sessionMemberId, QuestType.WEEKLY, true, completedDateTime);
		AssignQuest monthlyQuest = AssignQuestFixture.assignQuest(sessionMemberId, QuestType.MONTHLY, true, completedDateTime);
		assignQuestRepository.saveAll(List.of(dailyQuest, weeklyQuest, monthlyQuest));

		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.param("isCompleted", true)
			.param("startDateTime", LocalDateTime.of(2025, 7, 16, 0, 0, 0).toString())
			.param("endDateTime", LocalDateTime.of(2025, 7, 16, 23, 59, 59).toString())
			.when()
			.get("/quests/assigns/calendars")
			.then()
			.statusCode(OK.value())
			.body("assignQuests", hasSize(3));
	}
}
