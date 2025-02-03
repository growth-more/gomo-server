package com.gomo.app.quest.documentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.*;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.common.exception.DomainErrorCode;
import com.gomo.app.common.util.LoginMemberHelper;
import com.gomo.app.quest.common.util.AssignQuestDataHelper;
import com.gomo.app.quest.documentation.snippet.CreateAssignQuestSnippet;
import com.gomo.app.quest.domain.model.QuestType;
import com.gomo.app.quest.presentation.request.CreateAssignQuestRequest;

@DisplayName("[Presentation documentation]: 할당 퀘스트 생성 테스트")
public class CreateAssignQuestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = CreateAssignQuestSnippet.create();
	private final RestDocumentationFilter errorFilter = CreateAssignQuestSnippet.createError();

	@Autowired
	private LoginMemberHelper loginHelper;

	@Autowired
	private AssignQuestDataHelper assignQuestDataHelper;

	@BeforeEach
	public void setUp() {
		// sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
	}

	@AfterEach
	void tearDown() {
		assignQuestDataHelper.cleanUp();
	}

	@DisplayName("사용자가 할당 퀘스트를 생성한다.")
	@Test
	void create_assign_quest() {
		given(this.specification).filter(filter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.body(CreateAssignQuestRequest.of(
				UUID.randomUUID(),
				"subject name",
				QuestType.DAILY,
				"quest content"
			))
			.when()
			.post("/quests/assigns")
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
				UUID.randomUUID(),
				"",
				QuestType.DAILY,
				""))
			.when()
			.post("/quests/assigns")
			.then()
			.statusCode(UNPROCESSABLE_ENTITY.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo(DomainErrorCode.INVALID_PARAMETER.getHttpStatus()))
			.body("code", equalTo(DomainErrorCode.INVALID_PARAMETER.name()))
			.body("message", equalTo("Quest content cannot be blank"))
			.body("path", equalTo("/quests/assigns"));
	}
}
