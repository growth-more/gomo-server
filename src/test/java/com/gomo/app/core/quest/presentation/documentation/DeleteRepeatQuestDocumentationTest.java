package com.gomo.app.core.quest.presentation.documentation;

import static io.restassured.RestAssured.*;
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

import com.gomo.app.core.quest.presentation.documentation.snippet.DeleteRepeatQuestSnippet;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.core.quest.presentation.api.RepeatQuestApi;
import com.gomo.app.core.quest.presentation.api.request.CreateRepeatQuestRequest;
import com.gomo.app.test.DocumentationTestBase;

@DisplayName("[Presentation documentation]: 반복 퀘스트 삭제 테스트")
public class DeleteRepeatQuestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = DeleteRepeatQuestSnippet.create();

	@Autowired
	private RepeatQuestApi repeatQuestApi;
	private UUID repeatQuestId;

	@Autowired
	private RepeatQuestRepository repeatQuestRepository;

	@BeforeEach
	public void setUp() {
		repeatQuestId = repeatQuestApi.create(super.authInfo, getCreateRepeatQuestRequest()).getBody().getId();
	}

	@AfterEach
	void tearDown() {
		repeatQuestRepository.deleteAllInBatch();
	}

	@DisplayName("사용자가 반복 퀘스트를 삭제한다.")
	@Test
	void delete_repeat_quest() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.when()
			.delete("/quests/repeats/{id}", repeatQuestId)
			.then()
			.statusCode(NO_CONTENT.value());
	}

	private static @NotNull CreateRepeatQuestRequest getCreateRepeatQuestRequest() {
		return CreateRepeatQuestRequest.of(UUID.randomUUID(), "subject name", QuestType.MONTHLY.name(), "quest content");
	}
}
