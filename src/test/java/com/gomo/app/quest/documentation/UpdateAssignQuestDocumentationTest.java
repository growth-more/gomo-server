package com.gomo.app.quest.documentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.quest.documentation.snippet.UpdateAssignQuestSnippet;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.exception.code.QuestContentErrorCode;
import com.gomo.app.quest.fixture.AssignQuestFixture;
import com.gomo.app.quest.presentation.request.UpdateAssignQuestRequest;

@DisplayName("[Presentation documentation]: 참여 중인 퀘스트 수정 테스트")
public class UpdateAssignQuestDocumentationTest extends DocumentationTestBase {

	private final static String BLANK_QUEST_CONTENT = "";

	private final RestDocumentationFilter filter = UpdateAssignQuestSnippet.create();
	private final RestDocumentationFilter errorFilter = UpdateAssignQuestSnippet.createError();

	@Autowired
	private AssignQuestRepository assignQuestRepository;
	private AssignQuest assignQuest;

	@BeforeEach
	public void setUp() {
		assignQuest = AssignQuestFixture.assignQuest(sessionMemberId, false);
		assignQuestRepository.save(assignQuest);
	}

	@AfterEach
	void tearDown() {
		assignQuestRepository.deleteAllInBatch();
	}

	@DisplayName("사용자가 할당 퀘스트를 수정한다.")
	@Test
	void update_assign_quest() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.body(UpdateAssignQuestRequest.of(
				UUID.randomUUID(),
				"updated subject name",
				QuestType.DAILY.name(),
				"updated quest content"
			))
			.when()
			.put("/quests/assigns/{id}", assignQuest.getId().getId())
			.then()
			.statusCode(NO_CONTENT.value());
	}

	@DisplayName("사용자가 퀘스트 내용을 입력하지 않고 할당 퀘스트를 수정한다.")
	@Test
	void update_assign_quest_invalid_quest_content() {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.body(UpdateAssignQuestRequest.of(
				UUID.randomUUID(),
				"subject name",
				QuestType.DAILY.name(),
				BLANK_QUEST_CONTENT))
			.when()
			.put("/quests/assigns/{id}", assignQuest.getId().getId())
			.then()
			.statusCode(QuestContentErrorCode.BLANK.getHttpStatus())
			.body("timestamp", instanceOf(String.class))
			.body("path", equalTo("/quests/assigns/" + assignQuest.getId().getId()))
			.body("httpStatus", equalTo(QuestContentErrorCode.BLANK.getHttpStatus()))
			.body("code", equalTo(QuestContentErrorCode.BLANK.getErrorCode()))
			.body("message", equalTo(QuestContentErrorCode.BLANK.getMessage()));
	}
}
