package com.gomo.app.quest.documentation;

import static io.restassured.RestAssured.*;
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
import com.gomo.app.quest.common.util.AssignQuestDataHelper;
import com.gomo.app.quest.common.util.PointDataHelper;
import com.gomo.app.quest.common.util.StreakDataHelper;
import com.gomo.app.quest.documentation.snippet.CompleteAssignQuestSnippet;

public class CompleteAssignQuestDocumentationTest extends DocumentationTestBase {

	private static final String COMPLETE_ASSIGN_QUEST_URL = "/quests/assigns/{id}/complete";

	private final RestDocumentationFilter filter = CompleteAssignQuestSnippet.create();

	@Autowired
	private LoginMemberHelper loginHelper;

	@Autowired
	private AssignQuestDataHelper assignQuestDataHelper;

	@Autowired
	private StreakDataHelper streakDataHelper;

	@Autowired
	private PointDataHelper pointDataHelper;

	@BeforeEach
	public void setUp() {
		sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
	}

	@AfterEach
	void tearDown() {
		assignQuestDataHelper.cleanUp();
		streakDataHelper.cleanUp();
		pointDataHelper.cleanUp();
	}

	@DisplayName("사용자가 할당 퀘스트를 완료한다.")
	@Test
	void complete_assign_quest() {
		given(this.specification).filter(filter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.when()
			.put(COMPLETE_ASSIGN_QUEST_URL, "")
			.then()
			.statusCode(NO_CONTENT.value());
	}
}
