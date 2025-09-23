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
import com.gomo.app.displayorder.UpdatedOrderDto;
import com.gomo.app.quest.documentation.snippet.OrderUpdateRepeatQuestSnippet;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.domain.model.RepeatQuest;
import com.gomo.app.quest.domain.repository.RepeatQuestRepository;
import com.gomo.app.quest.fixture.RepeatQuestFixture;
import com.gomo.app.quest.presentation.request.OrderUpdateRepeatQuestRequest;

@DisplayName("[Presentation documentation]: 반복 퀘스트 순서 변경 테스트")
public class OrderUpdateRepeatQuestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = OrderUpdateRepeatQuestSnippet.create();

	@Autowired
	private RepeatQuestRepository repeatQuestRepository;
	private RepeatQuest repeatQuest1;
	private RepeatQuest repeatQuest2;

	@BeforeEach
	public void setUp() {
		repeatQuest1 = RepeatQuestFixture.repeatQuest(sessionMemberId, 1);
		repeatQuest2 = RepeatQuestFixture.repeatQuest(sessionMemberId, 2);
		repeatQuestRepository.saveAll(List.of(repeatQuest1, repeatQuest2));
	}

	@AfterEach
	void tearDown() {
		repeatQuestRepository.deleteAllInBatch();
	}

	@DisplayName("사용자가 반복 퀘스트의 정렬 순서를 변경한다.")
	@Test
	void update_repeat_quest_order() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.body(getRequest())
			.when()
			.put("/quests/repeats/orders")
			.then()
			.statusCode(NO_CONTENT.value());
	}

	private @NotNull OrderUpdateRepeatQuestRequest getRequest() {
		return OrderUpdateRepeatQuestRequest.of(
			QuestType.DAILY,
			List.of(
				UpdatedOrderDto.of(repeatQuest1.getId().getId(), 2),
				UpdatedOrderDto.of(repeatQuest2.getId().getId(), 1)
			)
		);
	}
}
