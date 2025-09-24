package com.gomo.app.quest.documentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.quest.documentation.snippet.ListAssignQuestSnippet;
import com.gomo.app.core.quest.domain.model.AssignQuest;
import com.gomo.app.core.quest.domain.model.QuestType;
import com.gomo.app.core.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.fixture.AssignQuestFixture;

@DisplayName("[Presentation documentation]: 참여중인 퀘스트 조회 테스트")
public class ListAssignQuestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = ListAssignQuestSnippet.create();

	@Autowired
	private AssignQuestRepository assignQuestRepository;
	private AssignQuest dailyQuest;
	private AssignQuest weeklyQuest;
	private AssignQuest monthlyQuest;

	@BeforeEach
	public void setUp() {
		dailyQuest = AssignQuestFixture.assignQuest(sessionMemberId, QuestType.DAILY, LocalDateTime.now());
		weeklyQuest = AssignQuestFixture.assignQuest(sessionMemberId, QuestType.WEEKLY, LocalDateTime.now());
		monthlyQuest = AssignQuestFixture.assignQuest(sessionMemberId, QuestType.MONTHLY, LocalDateTime.now());
		assignQuestRepository.saveAll(List.of(dailyQuest, weeklyQuest, monthlyQuest));
	}

	@AfterEach
	void tearDown() {
		assignQuestRepository.deleteAllInBatch();
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
			.body("weeklyQuests", hasSize(1))
			.body("monthlyQuests", hasSize(1));
	}
}
