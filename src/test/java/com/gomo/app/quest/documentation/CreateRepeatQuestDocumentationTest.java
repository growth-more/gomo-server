package com.gomo.app.quest.documentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.interest.common.dataprovider.InterestDataProvider;
import com.gomo.app.interest.domain.model.Interest;
import com.gomo.app.quest.common.util.RepeatQuestDataHelper;
import com.gomo.app.quest.documentation.snippet.CreateRepeatQuestSnippet;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.exception.code.QuestContentErrorCode;
import com.gomo.app.quest.exception.code.RepeatQuestErrorCode;
import com.gomo.app.quest.presentation.request.CreateRepeatQuestRequest;

@DisplayName("[Presentation documentation]: 반복 퀘스트 생성 테스트")
public class CreateRepeatQuestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = CreateRepeatQuestSnippet.create();
	private final RestDocumentationFilter errorFilter = CreateRepeatQuestSnippet.createError();

	@Autowired
	private RepeatQuestDataHelper repeatQuestDataHelper;

	@Autowired
	private InterestDataProvider interestDataProvider;
	private Interest subject;

	@BeforeEach
	public void setUp() {
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
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.body(CreateRepeatQuestRequest.of(
				subject.getId().getId(),
				"subject name",
				QuestType.DAILY,
				"quest content"))
			.when()
			.post("/quests/repeats")
			.then()
			.statusCode(CREATED.value())
			.body("id", hasLength(36));
	}

	@DisplayName("사용자가 퀘스트 내용을 입력하지 않고 반복 퀘스트를 생성한다.")
	@Test
	void create_repeat_quest_invalid_quest_content() {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.body(CreateRepeatQuestRequest.of(
				subject.getId().getId(),
				"subject name",
				QuestType.DAILY,
				" "))
			.when()
			.post("/quests/repeats")
			.then()
			.statusCode(QuestContentErrorCode.BLANK.getHttpStatus())
			.body("timestamp", instanceOf(String.class))
			.body("path", equalTo("/quests/repeats"))
			.body("httpStatus", equalTo(QuestContentErrorCode.BLANK.getHttpStatus()))
			.body("code", equalTo(QuestContentErrorCode.BLANK.getErrorCode()))
			.body("message", equalTo(QuestContentErrorCode.BLANK.getMessage()));
	}

	@DisplayName("사용자가 퀘스트 제한 개수를 초과하는 반복 퀘스트를 생성한다.")
	@Test
	void create_repeat_quest_exceeding_threshold() {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.body(CreateRepeatQuestRequest.of(
				subject.getId().getId(),
				"subject name",
				QuestType.MONTHLY,
				"quest content"))
			.when()
			.post("/quests/repeats")
			.then()
			.statusCode(RepeatQuestErrorCode.THRESHOLD_EXCEEDED.getHttpStatus())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo(RepeatQuestErrorCode.THRESHOLD_EXCEEDED.getHttpStatus()))
			.body("code", equalTo(RepeatQuestErrorCode.THRESHOLD_EXCEEDED.getErrorCode()))
			.body("message", equalTo(RepeatQuestErrorCode.THRESHOLD_EXCEEDED.getMessage()))
			.body("path", equalTo("/quests/repeats"));
	}
}
