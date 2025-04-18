package com.gomo.app.quest.documentation;

import static io.restassured.RestAssured.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.interest.presentation.request.UpdateOrderRequest;
import com.gomo.app.quest.common.dataprovider.AssignQuestDataProvider;
import com.gomo.app.quest.common.util.AssignQuestDataHelper;
import com.gomo.app.quest.documentation.snippet.OrderUpdateAssignQuestSnippet;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.presentation.request.OrderUpdateAssignQuestRequest;

@DisplayName("[Presentation documentation]: 참여 중인 퀘스트 순서 변경 테스트")
public class OrderUpdateAssignQuestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = OrderUpdateAssignQuestSnippet.create();

	@Autowired
	private AssignQuestDataHelper assignQuestDataHelper;

	@Autowired
	private AssignQuestDataProvider assignQuestDataProvider;
	private AssignQuest assignQuest;

	@BeforeEach
	void setup() {
		assignQuest = assignQuestDataProvider.dailyParticipatingQuest();
	}

	@AfterEach
	void tearDown() {
		assignQuestDataHelper.cleanUp();
	}

	@DisplayName("사용자가 참여 중인 퀘스트의 정렬 순서를 변경한다.")
	@Test
	void update_assign_quest_order() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.body(getRequest())
			.when()
			.put("/quests/assigns/orders")
			.then()
			.statusCode(NO_CONTENT.value());
	}

	// TODO <jhl221123>: 픽스처 관리방식 변경 후, 두 개 이상 원소를 사용할 것
	private @NotNull OrderUpdateAssignQuestRequest getRequest() {
		return OrderUpdateAssignQuestRequest.of(
			QuestType.DAILY,
			List.of(
				UpdateOrderRequest.of(assignQuest.getId().getId(), 1)
			)
		);
	}
}
