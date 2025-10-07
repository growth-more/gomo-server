package com.gomo.app.core.quest.documentation;

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
import com.gomo.app.core.interest.domain.repository.InterestRepository;
import com.gomo.app.core.interest.presentation.api.InterestApi;
import com.gomo.app.core.interest.presentation.api.request.CreateInterestRequest;
import com.gomo.app.core.member.presentation.QuestPropertyApi;
import com.gomo.app.core.member.presentation.request.UpdateQuestPropertyRequest;
import com.gomo.app.core.quest.documentation.snippet.CreateRepeatQuestSnippet;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.core.quest.exception.code.QuestContentErrorCode;
import com.gomo.app.core.quest.exception.code.QuestErrorCode;
import com.gomo.app.core.quest.presentation.request.CreateRepeatQuestRequest;

@DisplayName("[Presentation documentation]: 반복 퀘스트 생성 테스트")
public class CreateRepeatQuestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = CreateRepeatQuestSnippet.create();
	private final RestDocumentationFilter errorFilter = CreateRepeatQuestSnippet.createError();

	@Autowired
	private InterestApi interestApi;

	@Autowired
	private QuestPropertyApi questPropertyApi;

	private UUID subjectId;

	@BeforeEach
	public void setUp() {
		subjectId = interestApi.create(super.authInfo, CreateInterestRequest.of("name", "#FF0000", null))
			.getBody()
			.getId();
	}

	@Autowired
	private InterestRepository interestRepository;

	@Autowired
	private AssignQuestRepository assignQuestRepository;

	@AfterEach
	void tearDown() {
		interestRepository.deleteAllInBatch();
		assignQuestRepository.deleteAllInBatch();
	}

	@DisplayName("사용자가 반복 퀘스트를 생성한다.")
	@Test
	void create_repeat_quest() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.body(CreateRepeatQuestRequest.of(
				subjectId,
				"subject name",
				QuestType.DAILY.name(),
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
				subjectId,
				"subject name",
				QuestType.DAILY.name(),
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
		questPropertyApi.update(super.authInfo, UpdateQuestPropertyRequest.of(0, 0, 0));
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.body(CreateRepeatQuestRequest.of(
				subjectId,
				"subject name",
				QuestType.MONTHLY.name(),
				"quest content"))
			.when()
			.post("/quests/repeats")
			.then()
			.statusCode(QuestErrorCode.EXCEED_QUOTA.getHttpStatus())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo(QuestErrorCode.EXCEED_QUOTA.getHttpStatus()))
			.body("code", equalTo(QuestErrorCode.EXCEED_QUOTA.getErrorCode()))
			.body("message", equalTo(QuestErrorCode.EXCEED_QUOTA.getMessage()))
			.body("path", equalTo("/quests/repeats"));
	}
}
