package com.gomo.app.quest.documentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.*;

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
import com.gomo.app.quest.common.fixture.repeat.JavaRepeatQuestFixture;
import com.gomo.app.quest.common.fixture.repeat.SpringRepeatQuestFixture;
import com.gomo.app.quest.documentation.snippet.ListRepeatQuestSnippet;
import com.gomo.app.quest.domain.model.QuestType;

public class ListRepeatQuestDocumentationTest extends DocumentationTestBase {

	private static final String REPEAT_QUEST_URL = "/quests/repeats";

	private final RestDocumentationFilter filter = ListRepeatQuestSnippet.create();

	@Autowired
	private LoginMemberHelper loginHelper;

	@BeforeEach
	public void setUp() {
		sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
	}

	@DisplayName("사용자가 반복 퀘스트 목록을 조회한다.")
	@Test
	void list_assign_quest() {
		given(this.specification).filter(filter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.param("questType", QuestType.DAILY.name())
			.when()
			.get(REPEAT_QUEST_URL)
			.then()
			.statusCode(OK.value())
			.body("repeatQuests", hasSize(2))
			.body("repeatQuests.id", hasItems(
				JavaRepeatQuestFixture.id(),
				SpringRepeatQuestFixture.id()
			));
	}
}
