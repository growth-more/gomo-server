package com.gomo.app.quest.documentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.common.util.LoginMemberHelper;
import com.gomo.app.quest.common.dataprovider.AssignQuestDataProvider;
import com.gomo.app.quest.documentation.snippet.CalendarAssignQuestSnippet;
import com.gomo.app.quest.domain.model.AssignQuest;

@DisplayName("[Presentation documentation]: 할당 퀘스트 캘린더 조회 테스트")
public class CalendarAssignQuestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = CalendarAssignQuestSnippet.create();

	@Autowired
	private LoginMemberHelper loginHelper;

	@Autowired
	private AssignQuestDataProvider assignQuestDataProvider;
	private AssignQuest dailyQuest;
	private AssignQuest weeklyQuest;
	private AssignQuest monthlyQuest;

	@BeforeEach
	public void setUp() {
		// sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
		dailyQuest = assignQuestDataProvider.dailyParticipatingQuest();
		weeklyQuest = assignQuestDataProvider.weeklyParticipatingQuest();
		monthlyQuest = assignQuestDataProvider.monthlyParticipatingQuest();
	}

	@DisplayName("사용자가 할당 퀘스트의 한달 치 캘린더를 조회한다.")
	@Test
	void calendar_assign_quest() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.param("year", LocalDate.now().getYear())
			.param("month", LocalDate.now().getMonth().getValue())
			.when()
			.get("/quests/assigns/calendars")
			.then()
			.statusCode(OK.value())
			.body("assignQuests", hasSize(3))
			.body("assignQuests.id", containsInAnyOrder(
				dailyQuest.getId().toString(),
				weeklyQuest.getId().toString(),
				monthlyQuest.getId().toString()
			))
			.body("assignQuests.questType", containsInAnyOrder(
				dailyQuest.getQuest().getType().name(),
				weeklyQuest.getQuest().getType().name(),
				monthlyQuest.getQuest().getType().name()
			))
			.body("assignQuests.subjectName", containsInAnyOrder(
				dailyQuest.getQuest().getSubjectName().toString(),
				weeklyQuest.getQuest().getSubjectName().toString(),
				monthlyQuest.getQuest().getSubjectName().toString()
			))
			.body("assignQuests.content", containsInAnyOrder(
				dailyQuest.getQuest().getContent().toString(),
				weeklyQuest.getQuest().getContent().toString(),
				monthlyQuest.getQuest().getContent().toString()
			))
			.body("assignQuests.proof", containsInAnyOrder(
				dailyQuest.getProof().toString(),
				weeklyQuest.getProof().toString(),
				monthlyQuest.getProof().toString()
			))
			.body("assignQuests.completed", containsInAnyOrder(
				dailyQuest.isCompleted(),
				weeklyQuest.isCompleted(),
				monthlyQuest.isCompleted()
			))
			.body("assignQuests.completedDateTime", contains(
				dailyQuest.getCompletedDateTime(),
				weeklyQuest.getCompletedDateTime(),
				monthlyQuest.getCompletedDateTime()
			));
	}
}
