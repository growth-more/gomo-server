package com.gomo.app.quest.documentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
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
import com.gomo.app.quest.common.fixture.assign.JavaAssignQuestFixture;
import com.gomo.app.quest.common.util.AssignQuestDBDataHelper;
import com.gomo.app.quest.documentation.snippet.UpdateAssignQuestSnippet;
import com.gomo.app.quest.exception.AssignQuestErrorCode;
import com.gomo.app.quest.presentation.request.UpdateAssignQuestRequest;

public class UpdateAssignQuestDocumentationTest extends DocumentationTestBase {

	private static final String UPDATE_ASSIGN_QUEST_URL = "/quests/assigns/{id}";
	private final static String BLANK_QUEST_CONTENT = "";

	private final RestDocumentationFilter filter = UpdateAssignQuestSnippet.create();
	private final RestDocumentationFilter errorFilter = UpdateAssignQuestSnippet.createError();

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

	@DisplayName("사용자가 할당 퀘스트를 수정한다.")
	@Test
	void update_assign_quest() {
		given(this.specification).filter(filter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.body(UpdateAssignQuestRequest.of(
				JavaAssignQuestFixture.subjectId(),
				JavaAssignQuestFixture.questType(),
				JavaAssignQuestFixture.questContent()))
			.when()
			.put(UPDATE_ASSIGN_QUEST_URL, JavaAssignQuestFixture.id())
			.then()
			.statusCode(NO_CONTENT.value());
	}

	@DisplayName("사용자가 퀘스트 내용을 입력하지 않고 할당 퀘스트를 수정한다.")
	@Test
	void update_assign_quest_invalid_quest_content() {
		given(this.specification).filter(errorFilter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.body(UpdateAssignQuestRequest.of(
				JavaAssignQuestFixture.subjectId(),
				JavaAssignQuestFixture.questType(),
				BLANK_QUEST_CONTENT))
			.when()
			.put(UPDATE_ASSIGN_QUEST_URL)
			.then()
			.statusCode(UNPROCESSABLE_ENTITY.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo("422"))
			.body("code", equalTo(AssignQuestErrorCode.INVALID_PARAMETER.name()))
			.body("message", equalTo("Invalid parameter: " + BLANK_QUEST_CONTENT))
			.body("path", equalTo(UPDATE_ASSIGN_QUEST_URL));
	}
}
