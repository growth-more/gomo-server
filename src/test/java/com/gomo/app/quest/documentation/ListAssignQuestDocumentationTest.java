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
import com.gomo.app.quest.common.dataprovider.AssignQuestDataProvider;
import com.gomo.app.quest.documentation.snippet.ListAssignQuestSnippet;
import com.gomo.app.quest.domain.model.AssignQuest;

@DisplayName("[Presentation documentation]: 참여중인 퀘스트 조회 테스트")
public class ListAssignQuestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = ListAssignQuestSnippet.create();

	@Autowired
	private AssignQuestDataProvider assignQuestDataProvider;
	private AssignQuest dailyParticipatingQuest;
	private AssignQuest weeklyParticipatingQuest;
	private AssignQuest monthlyParticipatingQuest;

	@BeforeEach
	public void setUp() {
		dailyParticipatingQuest = assignQuestDataProvider.dailyParticipatingQuest();
		weeklyParticipatingQuest = assignQuestDataProvider.weeklyParticipatingQuest();
		monthlyParticipatingQuest = assignQuestDataProvider.monthlyParticipatingQuest();
	}

	@DisplayName("사용자가 현재 참여중인 퀘스트 목록을 조회한다.")
	@Test
	void list_assign_quest() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.when()
			.get("/quests/assigns")
			.then()
			.statusCode(OK.value())
			.body("dailyQuests", hasSize(1))
			.body("dailyQuests.id", containsInAnyOrder(dailyParticipatingQuest.getId().toString()))
			.body("dailyQuests.subjectId", containsInAnyOrder(dailyParticipatingQuest.getQuest().getSubjectId().toString()))
			.body("dailyQuests.questType", everyItem(equalTo(dailyParticipatingQuest.getQuest().getType().name())))
			.body("dailyQuests.point", everyItem(equalTo(10)))
			.body("dailyQuests.score", everyItem(equalTo(2)))
			.body("dailyQuests.subjectName", containsInAnyOrder(dailyParticipatingQuest.getQuest().getSubjectName().toString()))
			.body("dailyQuests.content", containsInAnyOrder(dailyParticipatingQuest.getQuest().getContent().toString()))
			.body("dailyQuests.confirmed", containsInAnyOrder(dailyParticipatingQuest.isConfirmed()))
			.body("dailyQuests.completed", containsInAnyOrder(dailyParticipatingQuest.isCompleted()))
			.body("dailyQuests.proof", containsInAnyOrder(dailyParticipatingQuest.getProof().toString()))
			.body("dailyQuests.startDateTime", notNullValue())
			.body("dailyQuests.displayOrder", containsInAnyOrder(dailyParticipatingQuest.getDisplayOrder().getDisplayOrder()))
			.body("weeklyQuests", hasSize(1))
			.body("weeklyQuests.id", containsInAnyOrder(weeklyParticipatingQuest.getId().toString()))
			.body("weeklyQuests.subjectId", containsInAnyOrder(weeklyParticipatingQuest.getQuest().getSubjectId().toString()))
			.body("weeklyQuests.questType", everyItem(equalTo(weeklyParticipatingQuest.getQuest().getType().name())))
			.body("weeklyQuests.point", everyItem(equalTo(150)))
			.body("weeklyQuests.score", everyItem(equalTo(20)))
			.body("weeklyQuests.subjectName", containsInAnyOrder(weeklyParticipatingQuest.getQuest().getSubjectName().toString()))
			.body("weeklyQuests.content", containsInAnyOrder(weeklyParticipatingQuest.getQuest().getContent().toString()))
			.body("weeklyQuests.confirmed", containsInAnyOrder(weeklyParticipatingQuest.isConfirmed()))
			.body("weeklyQuests.completed", containsInAnyOrder(weeklyParticipatingQuest.isCompleted()))
			.body("weeklyQuests.proof", containsInAnyOrder(weeklyParticipatingQuest.getProof().toString()))
			.body("weeklyQuests.startDateTime", notNullValue())
			.body("weeklyQuests.displayOrder", containsInAnyOrder(weeklyParticipatingQuest.getDisplayOrder().getDisplayOrder()))
			.body("monthlyQuests", hasSize(1))
			.body("monthlyQuests.id", containsInAnyOrder(monthlyParticipatingQuest.getId().toString()))
			.body("monthlyQuests.subjectId", containsInAnyOrder(monthlyParticipatingQuest.getQuest().getSubjectId().toString()))
			.body("monthlyQuests.questType", everyItem(equalTo(monthlyParticipatingQuest.getQuest().getType().name())))
			.body("monthlyQuests.point", everyItem(equalTo(1500)))
			.body("monthlyQuests.score", everyItem(equalTo(100)))
			.body("monthlyQuests.subjectName", containsInAnyOrder(monthlyParticipatingQuest.getQuest().getSubjectName().toString()))
			.body("monthlyQuests.content", containsInAnyOrder(monthlyParticipatingQuest.getQuest().getContent().toString()))
			.body("monthlyQuests.confirmed", containsInAnyOrder(monthlyParticipatingQuest.isConfirmed()))
			.body("monthlyQuests.completed", containsInAnyOrder(monthlyParticipatingQuest.isCompleted()))
			.body("monthlyQuests.proof", containsInAnyOrder(monthlyParticipatingQuest.getProof().toString()))
			.body("monthlyQuests.startDateTime", notNullValue())
			.body("monthlyQuests.displayOrder", containsInAnyOrder(monthlyParticipatingQuest.getDisplayOrder().getDisplayOrder()));
	}
}
