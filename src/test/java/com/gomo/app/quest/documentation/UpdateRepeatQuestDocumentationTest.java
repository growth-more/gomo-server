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
import com.gomo.app.common.exception.DomainErrorCode;
import com.gomo.app.quest.common.dataprovider.RepeatQuestDataProvider;
import com.gomo.app.quest.common.util.RepeatQuestDataHelper;
import com.gomo.app.quest.documentation.snippet.UpdateRepeatQuestSnippet;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.RepeatQuest;
import com.gomo.app.quest.presentation.request.UpdateRepeatQuestRequest;

@DisplayName("[Presentation documentation]: 반복 퀘스트 수정 테스트")
public class UpdateRepeatQuestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = UpdateRepeatQuestSnippet.create();
	private final RestDocumentationFilter errorFilter = UpdateRepeatQuestSnippet.createError();

	@Autowired
	private RepeatQuestDataHelper repeatQuestDataHelper;

	@Autowired
	private RepeatQuestDataProvider repeatQuestDataProvider;
	private RepeatQuest repeatQuest;

	@BeforeEach
	public void setUp() {
		repeatQuest = repeatQuestDataProvider.firstOrderDaily();
	}

	@AfterEach
	void tearDown() {
		repeatQuestDataHelper.cleanUp();
	}

	@DisplayName("사용자가 반복 퀘스트를 수정한다.")
	@Test
	void update_repeat_quest() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.body(UpdateRepeatQuestRequest.of(
				UUID.randomUUID(),
				"subject name",
				QuestType.DAILY,
				"quest content"))
			.when()
			.put("/quests/repeats/{id}", repeatQuest.getId().getId())
			.then()
			.statusCode(NO_CONTENT.value());
	}

	@DisplayName("사용자가 퀘스트 내용을 입력하지 않고 반복 퀘스트를 수정한다.")
	@Test
	void update_repeat_quest_invalid_quest_content() {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.body(UpdateRepeatQuestRequest.of(
				UUID.randomUUID(),
				"subject name",
				QuestType.DAILY,
				" "))
			.when()
			.put("/quests/repeats/{id}", repeatQuest.getId().getId())
			.then()
			.statusCode(UNPROCESSABLE_ENTITY.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo(DomainErrorCode.INVALID_PARAMETER.getHttpStatus()))
			.body("code", equalTo(DomainErrorCode.INVALID_PARAMETER.name()))
			.body("message", equalTo("Quest content cannot be blank"))
			.body("path", equalTo("/quests/repeats/" + repeatQuest.getId().getId()));
	}
}
