package com.gomo.app.core.quest.documentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.core.quest.documentation.snippet.ListRepeatQuestSnippet;
import com.gomo.app.core.quest.domain.model.QuestType;
import com.gomo.app.core.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.core.quest.presentation.RepeatQuestApi;
import com.gomo.app.core.quest.presentation.request.CreateRepeatQuestRequest;

@DisplayName("[Presentation documentation]: 반복 퀘스트 조회 테스트")
public class ListRepeatQuestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = ListRepeatQuestSnippet.create();

	@Autowired
	private RepeatQuestApi repeatQuestApi;

	@Autowired
	private RepeatQuestRepository repeatQuestRepository;

	@BeforeEach
	public void setUp() {
		repeatQuestApi.create(super.authInfo, getCreateRepeatQuestRequest(QuestType.DAILY.name())).getBody().getId();
		repeatQuestApi.create(super.authInfo, getCreateRepeatQuestRequest(QuestType.WEEKLY.name())).getBody().getId();
		repeatQuestApi.create(super.authInfo, getCreateRepeatQuestRequest(QuestType.MONTHLY.name())).getBody().getId();
	}

	@AfterEach
	void tearDown() {
		repeatQuestRepository.deleteAllInBatch();
	}

	@DisplayName("사용자가 반복 퀘스트 목록을 조회한다.")
	@Test
	void list_assign_quest() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.param("questType")
			.when()
			.get("/quests/repeats")
			.then()
			.statusCode(OK.value())
			.body("dailyQuests", hasSize(1))
			.body("weeklyQuests", hasSize(1))
			.body("monthlyQuests", hasSize(1));
	}

	private static @NotNull CreateRepeatQuestRequest getCreateRepeatQuestRequest(String questType) {
		return CreateRepeatQuestRequest.of(UUID.randomUUID(), "subject name", questType, "quest content");
	}
}
