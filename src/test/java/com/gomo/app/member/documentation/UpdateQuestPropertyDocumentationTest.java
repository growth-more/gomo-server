package com.gomo.app.member.documentation;

import static com.gomo.app.member.exception.MemberErrorCode.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.common.util.LoginMemberHelper;
import com.gomo.app.common.fixture.TestMemberFixture;
import com.gomo.app.member.documentation.snippet.UpdateQuestPropertySnippet;
import com.gomo.app.member.presentation.request.UpdateQuestPropertyRequest;
import com.gomo.app.member.common.util.MemberDBDataHelper;

public class UpdateQuestPropertyDocumentationTest extends DocumentationTestBase {

	private static final String QUEST_PROPERTY_URL = "/members/properties/quests";

	private final RestDocumentationFilter filter = UpdateQuestPropertySnippet.create();
	private final RestDocumentationFilter errorFilter = UpdateQuestPropertySnippet.createError();

	private String sessionId;

	@Autowired
	LoginMemberHelper loginMemberHelper;

	@Autowired
	MemberDBDataHelper memberDBDataHelper;

	@BeforeEach
	public void setUp() {
		sessionId = loginMemberHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
	}

	@AfterEach
	public void tearDown() {
		memberDBDataHelper.cleanUp();
	}

	@DisplayName("사용자가 퀘스트 설정 값을 변경한다.")
	@Test
	void update_quest_property() {
		given(this.specification).filter(filter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.sessionId(sessionId)
			.body(UpdateQuestPropertyRequest.of(1, 1, 1))
			.when()
			.put(QUEST_PROPERTY_URL)
			.then()
			.statusCode(OK.value());
	}

	@DisplayName("사용자가 잘못된 수치로 퀘스트 설정 값을 변경한다.")
	@Test
	void update_quest_property_with_invalid_property() throws JsonProcessingException {
		given(this.specification).filter(errorFilter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.sessionId(sessionId)
			.body(UpdateQuestPropertyRequest.of(-1, 5, 5))
			.when()
			.put(QUEST_PROPERTY_URL)
			.then()
			.statusCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo("422"))
			.body("code", equalTo(QUEST_PROPERTY_UPDATE_NOT_ALLOWED.name()))
			.body("message", equalTo("Quest property must be within the range, but: " + -1))
			.body("path", equalTo(QUEST_PROPERTY_URL));
	}
}
