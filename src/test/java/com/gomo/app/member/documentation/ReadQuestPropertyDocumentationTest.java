package com.gomo.app.member.documentation;

import static io.restassured.RestAssured.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.MediaType.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.common.util.LoginMemberHelper;
import com.gomo.app.member.documentation.snippet.ReadQuestPropertySnippet;

@DisplayName("[Presentation documentation]: 퀘스트 설정 테스트")
public class ReadQuestPropertyDocumentationTest extends DocumentationTestBase {

	private static final String QUEST_PROPERTY_URL = "/members/properties/quests";

	private final RestDocumentationFilter filter = ReadQuestPropertySnippet.create();
	private static final String EMAIL = "gomotest2@naver.com";
	private static final String PASSWORD = "Gomotest1234@";

	private String token;

	@Autowired
	LoginMemberHelper loginMemberHelper;

	@BeforeEach
	public void setUp() {
		token = loginMemberHelper.getAccessToken(EMAIL, PASSWORD);
	}

	@DisplayName("사용자가 퀘스트 설정값을 확인한다.")
	@Test
	void read_quest_property() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + token)
			.when()
			.get(QUEST_PROPERTY_URL)
			.then()
			.statusCode(OK.value())
			.body("dailyThreshold", equalTo(7))
			.body("weeklyThreshold", equalTo(7))
			.body("monthlyThreshold", equalTo(7));
	}
}
