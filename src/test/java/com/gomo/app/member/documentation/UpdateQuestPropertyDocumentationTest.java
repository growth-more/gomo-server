package com.gomo.app.member.documentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
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
import com.gomo.app.member.common.util.MemberDBDataHelper;
import com.gomo.app.member.documentation.snippet.UpdateQuestPropertySnippet;
import com.gomo.app.member.exception.code.QuestPropertyErrorCode;
import com.gomo.app.member.presentation.request.UpdateQuestPropertyRequest;

@DisplayName("[Presentation documentation]: 퀘스트 설정 변경 테스트")
public class UpdateQuestPropertyDocumentationTest extends DocumentationTestBase {

	private static final String URL = "/members/properties/quests";

	private final RestDocumentationFilter filter = UpdateQuestPropertySnippet.create();
	private final RestDocumentationFilter errorFilter = UpdateQuestPropertySnippet.createError();

	private static final String EMAIL = "gomotest@naver.com";
	private static final String PASSWORD = "Gomotest1234@";

	private String token;

	@Autowired
	LoginMemberHelper loginMemberHelper;

	@Autowired
	MemberDBDataHelper memberDBDataHelper;

	@BeforeEach
	public void setUp() {
		token = loginMemberHelper.getAccessToken(EMAIL, PASSWORD);
	}

	@AfterEach
	public void tearDown() {
		memberDBDataHelper.cleanUp();
	}

	@DisplayName("퀘스트 설정 값을 변경한다.")
	@Test
	void update_quest_property() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + token)
			.body(UpdateQuestPropertyRequest.of(1, 1, 1))
			.when()
			.put(URL)
			.then()
			.statusCode(NO_CONTENT.value());
	}

	@DisplayName("범위를 벗어나는 퀘스트 설정 값으로 변경한다.")
	@Test
	void update_quest_property_with_invalid_threshold_range() {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + token)
			.body(UpdateQuestPropertyRequest.of(-1, 5, 5))
			.when()
			.put(URL)
			.then()
			.statusCode(QuestPropertyErrorCode.TOO_SMALL.getHttpStatus())
			.body("timestamp", instanceOf(String.class))
			.body("path", equalTo(URL))
			.body("httpStatus", equalTo(QuestPropertyErrorCode.TOO_SMALL.getHttpStatus()))
			.body("code", equalTo(QuestPropertyErrorCode.TOO_SMALL.getErrorCode()))
			.body("message", equalTo(QuestPropertyErrorCode.TOO_SMALL.getMessage()));
	}
}
