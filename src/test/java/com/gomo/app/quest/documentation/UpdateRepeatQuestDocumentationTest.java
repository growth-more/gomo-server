package com.gomo.app.quest.documentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.*;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.common.fixture.TestMemberFixture;
import com.gomo.app.common.util.LoginMemberHelper;
import com.gomo.app.quest.common.constant.NonExistQuestField;
import com.gomo.app.quest.common.util.RepeatQuestDataHelper;
import com.gomo.app.quest.documentation.snippet.UpdateRepeatQuestSnippet;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.exception.RepeatQuestErrorCode;
import com.gomo.app.quest.presentation.request.UpdateRepeatQuestRequest;

public class UpdateRepeatQuestDocumentationTest extends DocumentationTestBase {

	private static final String UPDATE_REPEAT_QUEST_URL = "/quests/repeats/{id}";
	private final static String BLANK_QUEST_CONTENT = "";

	private final RestDocumentationFilter filter = UpdateRepeatQuestSnippet.create();
	private final RestDocumentationFilter errorFilter = UpdateRepeatQuestSnippet.createError();

	@Autowired
	private LoginMemberHelper loginHelper;

	@Autowired
	private RepeatQuestDataHelper repeatQuestDataHelper;

	@BeforeEach
	public void setUp() {
		sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
	}

	@AfterEach
	void tearDown() {
		repeatQuestDataHelper.cleanUp();
	}

	@DisplayName("사용자가 반복 퀘스트를 수정한다.")
	@Test
	void update_repeat_quest() {
		given(this.specification).filter(filter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.body(UpdateRepeatQuestRequest.of(
				UUID.randomUUID(),
				QuestType.DAILY,
				NonExistQuestField.CONTENT))
			.when()
			.put(UPDATE_REPEAT_QUEST_URL, "")
			.then()
			.statusCode(NO_CONTENT.value());
	}

	@DisplayName("사용자가 퀘스트 내용을 입력하지 않고 반복 퀘스트를 수정한다.")
	@Test
	void update_repeat_quest_invalid_quest_content() {
		given(this.specification).filter(errorFilter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.body(UpdateRepeatQuestRequest.of(
				UUID.randomUUID(),
				QuestType.DAILY,
				BLANK_QUEST_CONTENT))
			.when()
			.put(UPDATE_REPEAT_QUEST_URL, "")
			.then()
			.statusCode(UNPROCESSABLE_ENTITY.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo("422"))
			.body("code", equalTo(RepeatQuestErrorCode.INVALID_PARAMETER.name()))
			.body("message", equalTo("Invalid parameter: " + BLANK_QUEST_CONTENT))
			.body("path", equalTo(UPDATE_REPEAT_QUEST_URL));
	}
}
