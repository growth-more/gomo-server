package com.gomo.app.quest.documentation;

import static io.restassured.RestAssured.*;
import static org.springframework.http.HttpStatus.*;

import java.util.List;

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
import com.gomo.app.quest.common.util.RepeatQuestDataHelper;
import com.gomo.app.quest.documentation.snippet.OrderUpdateRepeatQuestSnippet;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.presentation.request.OrderUpdateRepeatQuestRequest;

public class OrderUpdateRepeatQuestDocumentationTest extends DocumentationTestBase {

	private static final String ORDER_UPDATE_REPEAT_QUEST_URL = "/quests/repeats/orders";

	private final RestDocumentationFilter filter = OrderUpdateRepeatQuestSnippet.create();

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

	@DisplayName("사용자가 반복 퀘스트의 정렬 순서를 변경한다.")
	@Test
	void update_repeat_quest_order() {
		given(this.specification).filter(filter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.body(OrderUpdateRepeatQuestRequest.of(
				QuestType.DAILY,
				List.of(1, 2)))
			.when()
			.put(ORDER_UPDATE_REPEAT_QUEST_URL)
			.then()
			.statusCode(NO_CONTENT.value());
	}
}
