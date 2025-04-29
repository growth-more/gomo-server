package com.gomo.app.quest.documentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.quest.common.util.AssignQuestDataHelper;
import com.gomo.app.quest.documentation.snippet.CreateAssignQuestSnippet;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.exception.code.QuestContentErrorCode;
import com.gomo.app.quest.exception.code.QuestErrorCode;
import com.gomo.app.quest.presentation.request.CreateAssignQuestRequest;

@DisplayName("[Presentation documentation]: 할당 퀘스트 생성 테스트")
public class CreateAssignQuestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = CreateAssignQuestSnippet.create();
	private final RestDocumentationFilter errorFilter = CreateAssignQuestSnippet.createError();

	@Autowired
	private AssignQuestDataHelper assignQuestDataHelper;

	@AfterEach
	void tearDown() {
		assignQuestDataHelper.cleanUp();
	}

	@DisplayName("사용자가 할당 퀘스트를 생성한다.")
	@Test
	void create_assign_quest() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.body(CreateAssignQuestRequest.of(
				UUID.randomUUID(),
				"subject name",
				QuestType.DAILY,
				"quest content"
			))
			.when()
			.post("/quests/assigns")
			.then()
			.statusCode(CREATED.value())
			.body("id", hasLength(36));
	}

	@DisplayName("사용자가 퀘스트 내용을 입력하지 않고 할당 퀘스트를 생성한다.")
	@Test
	void create_assign_quest_invalid_quest_content() {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.body(CreateAssignQuestRequest.of(
				UUID.randomUUID(),
				"",
				QuestType.DAILY,
				""))
			.when()
			.post("/quests/assigns")
			.then()
			.statusCode(QuestContentErrorCode.BLANK.getHttpStatus())
			.body("timestamp", instanceOf(String.class))
			.body("path", equalTo("/quests/assigns"))
			.body("httpStatus", equalTo(QuestContentErrorCode.BLANK.getHttpStatus()))
			.body("code", equalTo(QuestContentErrorCode.BLANK.getErrorCode()))
			.body("message", equalTo(QuestContentErrorCode.BLANK.getMessage()));
	}

	@DisplayName("사용자가 퀘스트 제한 개수를 초과하는 할당 퀘스트를 생성한다.")
	@Test
	void create_assign_quest_exceeding_threshold() {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.body(CreateAssignQuestRequest.of(
				UUID.randomUUID(),
				"subject name",
				QuestType.MONTHLY,
				"quest content"
			))
			.when()
			.post("/quests/assigns")
			.then()
			.statusCode(QuestErrorCode.EXCEED_QUOTA.getHttpStatus())
			.body("timestamp", instanceOf(String.class))
			.body("path", equalTo("/quests/assigns"))
			.body("httpStatus", equalTo(QuestErrorCode.EXCEED_QUOTA.getHttpStatus()))
			.body("code", equalTo(QuestErrorCode.EXCEED_QUOTA.getErrorCode()))
			.body("message", equalTo(QuestErrorCode.EXCEED_QUOTA.getMessage()));
	}
}
