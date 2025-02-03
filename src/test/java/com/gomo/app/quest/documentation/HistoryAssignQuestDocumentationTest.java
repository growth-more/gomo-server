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
import com.gomo.app.quest.common.dataprovider.AssignQuestDataProvider;
import com.gomo.app.quest.documentation.snippet.HistoryAssignQuestSnippet;
import com.gomo.app.quest.domain.model.AssignQuest;

@DisplayName("[Presentation documentation]: 할당 퀘스트 과거 이력 조회 테스트")
public class HistoryAssignQuestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = HistoryAssignQuestSnippet.create();

	@Autowired
	private LoginMemberHelper loginHelper;

	@Autowired
	private AssignQuestDataProvider assignQuestDataProvider;
	private AssignQuest notConfirmed;
	private AssignQuest confirmed;
	private AssignQuest completedJava;
	private AssignQuest completedSpring;
	private AssignQuest weeklyHistoryQuest;
	private AssignQuest monthlyHistoryQuest;

	@BeforeEach
	public void setUp() {
		// sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
		notConfirmed = assignQuestDataProvider.notConfirmed();
		confirmed = assignQuestDataProvider.confirmed();
		completedJava = assignQuestDataProvider.completedJava();
		completedSpring = assignQuestDataProvider.completedSpring();
		weeklyHistoryQuest = assignQuestDataProvider.weeklyHistoryQuest();
		monthlyHistoryQuest = assignQuestDataProvider.monthlyHistoryQuest();
	}

	@DisplayName("사용자가 할당 퀘스트의 과거 이력을 조회한다.")
	@Test
	void history_assign_quest() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.param("size", 10)
			.when()
			.get("/quests/assigns/histories")
			.then()
			.statusCode(OK.value())
			.body("dailyHistoryQuests", hasSize(4))
			.body("dailyHistoryQuests.id", containsInAnyOrder(
				notConfirmed.getId().toString(),
				confirmed.getId().toString(),
				completedJava.getId().toString(),
				completedSpring.getId().toString()
			))
			.body("dailyHistoryQuests.questType", everyItem(equalTo(notConfirmed.getQuest().getType().name())))
			.body("dailyHistoryQuests.subjectName", containsInAnyOrder(
				notConfirmed.getQuest().getSubjectName().toString(),
				confirmed.getQuest().getSubjectName().toString(),
				completedJava.getQuest().getSubjectName().toString(),
				completedSpring.getQuest().getSubjectName().toString()
			))
			.body("dailyHistoryQuests.content", containsInAnyOrder(
				notConfirmed.getQuest().getContent().toString(),
				confirmed.getQuest().getContent().toString(),
				completedJava.getQuest().getContent().toString(),
				completedSpring.getQuest().getContent().toString()
			))
			.body("dailyHistoryQuests.proof", containsInAnyOrder(
				notConfirmed.getProof().toString(),
				confirmed.getProof().toString(),
				completedJava.getProof().toString(),
				completedSpring.getProof().toString()
			))
			.body("dailyHistoryQuests.completed", containsInAnyOrder(
				notConfirmed.isCompleted(),
				confirmed.isCompleted(),
				completedJava.isCompleted(),
				completedSpring.isCompleted()
			))
			.body("dailyHistoryQuests.completedDateTime", containsInAnyOrder(
				null,
				null,
				completedJava.getCompletedDateTime().toString(),
				completedSpring.getCompletedDateTime().toString()
			))
			.body("weeklyHistoryQuests", hasSize(1))
			.body("weeklyHistoryQuests.id", containsInAnyOrder(weeklyHistoryQuest.getId().toString()))
			.body("weeklyHistoryQuests.questType", everyItem(equalTo(weeklyHistoryQuest.getQuest().getType().name())))
			.body("weeklyHistoryQuests.subjectName", containsInAnyOrder(weeklyHistoryQuest.getQuest().getSubjectName().toString()))
			.body("weeklyHistoryQuests.content", containsInAnyOrder(weeklyHistoryQuest.getQuest().getContent().toString()))
			.body("weeklyHistoryQuests.proof", containsInAnyOrder(weeklyHistoryQuest.getProof().toString()))
			.body("weeklyHistoryQuests.completed", containsInAnyOrder(weeklyHistoryQuest.isCompleted()))
			.body("weeklyHistoryQuests.completedDateTime", containsInAnyOrder(weeklyHistoryQuest.getCompletedDateTime().toString()))
			.body("monthlyHistoryQuests", hasSize(1))
			.body("monthlyHistoryQuests.id", containsInAnyOrder(monthlyHistoryQuest.getId().toString()))
			.body("monthlyHistoryQuests.questType", everyItem(equalTo(monthlyHistoryQuest.getQuest().getType().name())))
			.body("monthlyHistoryQuests.subjectName", containsInAnyOrder(monthlyHistoryQuest.getQuest().getSubjectName().toString()))
			.body("monthlyHistoryQuests.content", containsInAnyOrder(monthlyHistoryQuest.getQuest().getContent().toString()))
			.body("monthlyHistoryQuests.proof", containsInAnyOrder(monthlyHistoryQuest.getProof().toString()))
			.body("monthlyHistoryQuests.completed", containsInAnyOrder(monthlyHistoryQuest.isCompleted()))
			.body("monthlyHistoryQuests.completedDateTime", containsInAnyOrder(monthlyHistoryQuest.getCompletedDateTime().toString()));
	}
}
