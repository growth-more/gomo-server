package com.gomo.app.core.quest.adapter.in.api;

import static io.restassured.RestAssured.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.core.quest.adapter.in.api.request.ReRollAssignQuestRequest;
import com.gomo.app.core.quest.adapter.in.api.snippet.ReRollAssignQuestSnippet;
import com.gomo.app.core.quest.domain.model.assign.AssignQuest;
import com.gomo.app.core.quest.domain.model.pool.ProcessingStatus;
import com.gomo.app.core.quest.domain.model.pool.QuestPool;
import com.gomo.app.core.quest.domain.model.pool.SourceType;
import com.gomo.app.core.quest.domain.model.quest.QuestType;
import com.gomo.app.core.quest.domain.repository.AssignQuestRepository;
import com.gomo.app.core.quest.domain.repository.QuestPoolRepository;
import com.gomo.app.core.quest.fixture.AssignQuestFixture;
import com.gomo.app.core.quest.fixture.QuestFixture;
import com.gomo.app.test.DocumentationTestBase;

@DisplayName("[Presentation documentation]: 퀘스트 재생성(리롤) 테스트")
public class ReRollAssignQuestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = ReRollAssignQuestSnippet.create();

	@Autowired
	private QuestPoolRepository questPoolRepository;

	@Autowired
	private AssignQuestRepository assignQuestRepository;
	private AssignQuest assignQuest;

	@BeforeEach
	public void setUp() {
		assignQuest = AssignQuestFixture.create(sessionMemberId, QuestType.DAILY, LocalDateTime.now());
		assignQuestRepository.save(assignQuest);
		questPoolRepository.save(QuestPool.of(UUIDGenerator.generate(), QuestFixture.create(assignQuest.participantId()), ProcessingStatus.UNUSED, SourceType.AI));
	}

	@AfterEach
	void tearDown() {
		assignQuestRepository.deleteAllInBatch();
	}

	@DisplayName("사용자가 할당 퀘스트를 재생성(리롤)한다.")
	@Test
	void re_roll_assign_quest() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.body(ReRollAssignQuestRequest.of(assignQuest.getId()))
			.when()
			.post("/quests/assigns/re-roll")
			.then()
			.statusCode(CREATED.value());
	}
}
