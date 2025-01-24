package com.gomo.app.member.documentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.*;

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
import com.gomo.app.member.documentation.snippet.ReadQuestPropertySnippet;

public class ReadQuestPropertyDocumentationTest extends DocumentationTestBase {

	private static final String QUEST_PROPERTY_URL = "/members/properties/quests";

	private final RestDocumentationFilter filter = ReadQuestPropertySnippet.create();

	@Autowired
	LoginMemberHelper loginMemberHelper;

	@BeforeEach
	public void setUp() {
		sessionId = loginMemberHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
	}

	@DisplayName("사용자가 퀘스트 설정값을 확인한다.")
	@Test
	void read_quest_property() {
		given(this.specification).filter(filter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.sessionId(sessionId)
			.when()
			.get(QUEST_PROPERTY_URL)
			.then()
			.statusCode(OK.value())
			.body("dailyThreshold", is(TestMemberFixture.dailyThreshold()))
			.body("weeklyThreshold", is(TestMemberFixture.weeklyThreshold()))
			.body("monthlyThreshold", is(TestMemberFixture.monthlyThreshold()));
	}
}
