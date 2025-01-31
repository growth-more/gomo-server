package com.gomo.app.quest.documentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.*;

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
import com.gomo.app.interest.common.dataprovider.InterestDataProvider;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.quest.common.constant.NonExistQuestField;
import com.gomo.app.quest.common.util.RepeatQuestDataHelper;
import com.gomo.app.quest.documentation.snippet.CreateRepeatQuestSnippet;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.exception.AssignQuestErrorCode;
import com.gomo.app.quest.presentation.request.CreateRepeatQuestRequest;

public class CreateRepeatQuestDocumentationTest extends DocumentationTestBase {

	private static final String REPEAT_QUEST_URL = "/quests/repeats";
	private final static String BLANK_QUEST_CONTENT = "";

	private final RestDocumentationFilter filter = CreateRepeatQuestSnippet.create();
	private final RestDocumentationFilter errorFilter = CreateRepeatQuestSnippet.createError();

	@Autowired
	private LoginMemberHelper loginHelper;

	@Autowired
	private RepeatQuestDataHelper repeatQuestDataHelper;

	@Autowired
	private InterestDataProvider interestDataProvider;
	private Interest subject;

	@BeforeEach
	public void setUp() {
		sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
		subject = interestDataProvider.backend();
	}

	@AfterEach
	void tearDown() {
		repeatQuestDataHelper.cleanUp();
	}

	@DisplayName("사용자가 반복 퀘스트를 생성한다.")
	@Test
	void create_repeat_quest() {
		given(this.specification).filter(filter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.body(CreateRepeatQuestRequest.of(
				subject.getId().getId(),
				QuestType.DAILY,
				NonExistQuestField.CONTENT))
			.when()
			.post(REPEAT_QUEST_URL)
			.then()
			.statusCode(CREATED.value())
			.body("id", hasLength(36));
	}

	@DisplayName("사용자가 퀘스트 내용을 입력하지 않고 반복 퀘스트를 생성한다.")
	@Test
	void create_repeat_quest_invalid_quest_content() {
		given(this.specification).filter(errorFilter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.body(CreateRepeatQuestRequest.of(
				subject.getId().getId(),
				QuestType.DAILY,
				BLANK_QUEST_CONTENT))
			.when()
			.post(REPEAT_QUEST_URL)
			.then()
			.statusCode(UNPROCESSABLE_ENTITY.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo("422"))
			.body("code", equalTo(AssignQuestErrorCode.INVALID_PARAMETER.name()))
			.body("message", equalTo("Invalid parameter: " + BLANK_QUEST_CONTENT))
			.body("path", equalTo(REPEAT_QUEST_URL));
	}
}
