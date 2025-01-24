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
import com.gomo.app.quest.documentation.snippet.CreateAssignQuestSnippet;
import com.gomo.app.quest.exception.AssignQuestErrorCode;
import com.gomo.app.quest.presentation.request.CreateAssignQuestRequest;

public class CreateAssignQuestDocumentationTest extends DocumentationTestBase {

	private static final String ASSIGN_QUEST_URL = "/quests/assigns";
	private final static String BLANK_QUEST_CONTENT = "";

	private final RestDocumentationFilter filter = CreateAssignQuestSnippet.create();
	private final RestDocumentationFilter errorFilter = CreateAssignQuestSnippet.createError();

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

	@DisplayName("사용자가 할당 퀘스트를 생성한다.")
	@Test
	void create_assign_quest() {
		given(this.specification).filter(filter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.body(CreateAssignQuestRequest.of(
				JavaAssignQuestFixture.subjectId(),
				JavaAssignQuestFixture.questType(),
				JavaAssignQuestFixture.questContent()))
			.when()
			.post(ASSIGN_QUEST_URL)
			.then()
			.statusCode(CREATED.value())
			.body("id", hasLength(36));
	}

	@DisplayName("사용자가 퀘스트 내용을 입력하지 않고 할당 퀘스트를 생성한다.")
	@Test
	void create_assign_quest_invalid_quest_content() {
		given(this.specification).filter(errorFilter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.body(CreateAssignQuestRequest.of(
				JavaAssignQuestFixture.subjectId(),
				JavaAssignQuestFixture.questType(),
				BLANK_QUEST_CONTENT))
			.when()
			.post(ASSIGN_QUEST_URL)
			.then()
			.statusCode(UNPROCESSABLE_ENTITY.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo("422"))
			.body("code", equalTo(AssignQuestErrorCode.INVALID_PARAMETER.name()))
			.body("message", equalTo("Invalid parameter: " + BLANK_QUEST_CONTENT))
			.body("path", equalTo(ASSIGN_QUEST_URL));
	}
}
