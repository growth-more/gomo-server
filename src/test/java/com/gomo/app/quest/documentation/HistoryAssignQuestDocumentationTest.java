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
import com.gomo.app.quest.common.fixture.assign.HistoryAssignQuestFixture;
import com.gomo.app.quest.documentation.snippet.HistoryAssignQuestSnippet;
import com.gomo.app.quest.domain.model.QuestType;

public class HistoryAssignQuestDocumentationTest extends DocumentationTestBase {

	private static final String HISTORY_ASSIGN_QUEST_URL = "/quests/assigns/histories";

	private final RestDocumentationFilter filter = HistoryAssignQuestSnippet.create();

	@Autowired
	private LoginMemberHelper loginHelper;

	@BeforeEach
	public void setUp() {
		sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
	}

	// TODO <jhl221123>: id 외 다른 필드도 검증 필요
	@DisplayName("사용자가 수행한 할당 퀘스트의 과거 이력을 조회한다.")
	@Test
	void history_assign_quest() {
		given(this.specification).filter(filter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.sessionId(sessionId)
			.param("questType", QuestType.DAILY.name())
			.param("size", 1)
			.param("lastElementId", HistoryAssignQuestFixture.history1Id())
			.when()
			.get(HISTORY_ASSIGN_QUEST_URL)
			.then()
			.statusCode(OK.value())
			.body("histories", hasSize(1))
			.body("histories.id", hasItems(
				HistoryAssignQuestFixture.history2Id()
			));
	}
}
