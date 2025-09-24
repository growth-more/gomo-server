package com.gomo.app.quest.documentation;

import static io.restassured.RestAssured.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import java.time.LocalDateTime;
import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.common.displayorder.UpdatedOrderDto;
import com.gomo.app.quest.documentation.snippet.OrderUpdateAssignQuestSnippet;
import com.gomo.app.core.quest.domain.model.AssignQuest;
import com.gomo.app.core.quest.domain.model.QuestType;
import com.gomo.app.core.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.quest.fixture.AssignQuestFixture;
import com.gomo.app.core.quest.presentation.request.OrderUpdateAssignQuestRequest;

@DisplayName("[Presentation documentation]: 참여 중인 퀘스트 순서 변경 테스트")
public class OrderUpdateAssignQuestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = OrderUpdateAssignQuestSnippet.create();

	@Autowired
	private AssignQuestRepository assignQuestRepository;
	private AssignQuest assignQuest1;
	private AssignQuest assignQuest2;

	@BeforeEach
	public void setUp() {
		assignQuest1 = AssignQuestFixture.assignQuest(sessionMemberId, 1, LocalDateTime.now());
		assignQuest2 = AssignQuestFixture.assignQuest(sessionMemberId, 2, LocalDateTime.now());
		assignQuestRepository.saveAll(List.of(assignQuest1, assignQuest2));
	}

	@AfterEach
	void tearDown() {
		assignQuestRepository.deleteAllInBatch();
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

	private @NotNull OrderUpdateAssignQuestRequest getRequest() {
		return OrderUpdateAssignQuestRequest.of(
			QuestType.DAILY.name(),
			List.of(
				UpdatedOrderDto.of(assignQuest1.getId().getId(), 2),
				UpdatedOrderDto.of(assignQuest2.getId().getId(), 1)
			)
		);
	}
}
