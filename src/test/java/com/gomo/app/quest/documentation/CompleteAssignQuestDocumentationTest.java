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
import com.gomo.app.interest.common.util.InterestDataHelper;
import com.gomo.app.quest.common.dataprovider.AssignQuestDataProvider;
import com.gomo.app.quest.common.util.AssignQuestDataHelper;
import com.gomo.app.quest.common.util.PointDataHelper;
import com.gomo.app.quest.common.util.StreakDataHelper;
import com.gomo.app.quest.documentation.snippet.CompleteAssignQuestSnippet;
import com.gomo.app.quest.domain.model.AssignQuest;
import com.gomo.app.quest.presentation.request.CompleteAssignQuestRequest;

@DisplayName("[Presentation documentation]: 참여 중인 퀘스트 완료 테스트")
public class CompleteAssignQuestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = CompleteAssignQuestSnippet.create();

	@Autowired
	private AssignQuestDataHelper assignQuestDataHelper;

	@Autowired
	private InterestDataHelper interestDataHelper;

	@Autowired
	private StreakDataHelper streakDataHelper;

	@Autowired
	private PointDataHelper pointDataHelper;

	@Autowired
	private AssignQuestDataProvider assignQuestDataProvider;
	private AssignQuest confirmed;

	@BeforeEach
	public void setUp() {
		confirmed = assignQuestDataProvider.confirmed();
	}

	@AfterEach
	void tearDown() {
		assignQuestDataHelper.cleanUp();
		interestDataHelper.cleanUp();
		streakDataHelper.cleanUp();
		pointDataHelper.cleanUp();
	}

	@DisplayName("사용자가 할당 퀘스트를 완료한다.")
	@Test
	void complete_assign_quest() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.body(CompleteAssignQuestRequest.of("https://proof"))
			.when()
			.put("/quests/assigns/{id}/complete", confirmed.getId().getId())
			.then()
			.statusCode(NO_CONTENT.value());
	}
}
