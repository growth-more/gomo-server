package com.gomo.app.quest.documentation;

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

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.common.util.LoginMemberHelper;
import com.gomo.app.quest.common.dataprovider.AssignQuestDataProvider;
import com.gomo.app.quest.common.util.AssignQuestDataHelper;
import com.gomo.app.quest.documentation.snippet.DeleteAssignQuestSnippet;
import com.gomo.app.quest.domain.model.AssignQuest;

@DisplayName("[Presentation documentation]: 참여 중인 퀘스트 삭제 테스트")
public class DeleteAssignQuestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = DeleteAssignQuestSnippet.create();

	@Autowired
	private LoginMemberHelper loginHelper;

	@Autowired
	private AssignQuestDataHelper assignQuestDataHelper;

	@Autowired
	private AssignQuestDataProvider assignQuestDataProvider;
	private AssignQuest assignQuest;

	@BeforeEach
	public void setUp() {
		// sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
		assignQuest = assignQuestDataProvider.notConfirmed();
	}

	@AfterEach
	void tearDown() {
		assignQuestDataHelper.cleanUp();
	}

	@DisplayName("사용자가 할당 퀘스트를 삭제한다.")
	@Test
	void delete_assign_quest() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.when()
			.delete("/quests/assigns/{id}", assignQuest.getId().getId())
			.then()
			.statusCode(NO_CONTENT.value());
	}
}
