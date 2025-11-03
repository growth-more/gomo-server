package com.gomo.app.core.quest.adapter.in.api;

import static io.restassured.RestAssured.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.core.quest.adapter.in.api.snippet.ConfirmAssignQuestSnippet;
import com.gomo.app.core.quest.domain.model.assign.AssignQuest;
import com.gomo.app.core.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.core.quest.fixture.AssignQuestFixture;
import com.gomo.app.test.DocumentationTestBase;

@DisplayName("[Presentation documentation]: 참여 중인 퀘스트 확정 테스트")
public class ConfirmAssignQuestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = ConfirmAssignQuestSnippet.create();

	@Autowired
	private AssignQuestRepository assignQuestRepository;
	private AssignQuest assignQuest;

	@BeforeEach
	public void setUp() {
		assignQuest = AssignQuestFixture.create(sessionMemberId, false);
		assignQuestRepository.save(assignQuest);
	}

	@AfterEach
	void tearDown() {
		assignQuestRepository.deleteAllInBatch();
	}

	@DisplayName("사용자가 할당 퀘스트를 확정한다.")
	@Test
	void confirm_assign_quest() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.when()
			.put("/quests/assigns/{id}/confirm", assignQuest.getId())
			.then()
			.statusCode(NO_CONTENT.value());
	}
}
