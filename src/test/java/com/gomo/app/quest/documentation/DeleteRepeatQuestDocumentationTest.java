package com.gomo.app.quest.documentation;

import static io.restassured.RestAssured.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import java.util.UUID;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.quest.documentation.snippet.DeleteRepeatQuestSnippet;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.presentation.RepeatQuestApi;
import com.gomo.app.quest.presentation.request.CreateRepeatQuestRequest;

@DisplayName("[Presentation documentation]: 반복 퀘스트 삭제 테스트")
public class DeleteRepeatQuestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = DeleteRepeatQuestSnippet.create();

	@Autowired
	private RepeatQuestApi repeatQuestApi;
	private UUID repeatQuestId;

	@BeforeEach
	public void setUp() {
		repeatQuestId = repeatQuestApi.create(super.authInfo, getCreateRepeatQuestRequest()).getBody().getId();
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
		return CreateRepeatQuestRequest.of(UUID.randomUUID(), "subject name", QuestType.MONTHLY, "quest content");
	}
}
