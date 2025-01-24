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
import com.gomo.app.interest.presentation.request.OrderUpdateMajorInterestRequest;
import com.gomo.app.quest.common.fixture.assign.JavaAssignQuestFixture;
import com.gomo.app.quest.common.fixture.assign.SpringAssignQuestFixture;
import com.gomo.app.quest.common.util.AssignQuestDBDataHelper;
import com.gomo.app.quest.documentation.snippet.OrderUpdateAssignQuestSnippet;

public class OrderUpdateAssignQuestDocumentationTest extends DocumentationTestBase {

	private static final String ORDER_UPDATE_ASSIGN_QUEST_URL = "/quests/assigns/orders";

	private final RestDocumentationFilter filter = OrderUpdateAssignQuestSnippet.create();

	@Autowired
	private LoginMemberHelper loginHelper;

	@Autowired
	private AssignQuestDBDataHelper assignQuestDBDataHelper;

	@BeforeEach
	public void setUp() {
		sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
	}

	@AfterEach
	void tearDown() {
		assignQuestDBDataHelper.cleanUp();
	}

	@DisplayName("사용자가 할당 퀘스트의 정렬 순서를 변경한다.")
	@Test
	void update_assign_quest_order() {
		given(this.specification).filter(filter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.body(OrderUpdateMajorInterestRequest.of(List.of(SpringAssignQuestFixture.displayOrder(), JavaAssignQuestFixture.displayOrder())))
			.when()
			.put(ORDER_UPDATE_ASSIGN_QUEST_URL)
			.then()
			.statusCode(NO_CONTENT.value());
	}
}
