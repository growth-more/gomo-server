package com.gomo.app.member.documentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.common.util.LoginMemberHelper;
import com.gomo.app.member.common.util.MemberDBDataHelper;
import com.gomo.app.member.documentation.snippet.UpdateQuestPropertySnippet;
import com.gomo.app.member.presentation.request.UpdateQuestPropertyRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import static com.gomo.app.common.exception.DomainErrorCode.INVALID_PARAMETER;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.instanceOf;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@DisplayName("[Presentation documentation]: 퀘스트 설정 변경 테스트")
public class UpdateQuestPropertyDocumentationTest extends DocumentationTestBase {

	private static final String QUEST_PROPERTY_URL = "/members/properties/quests";

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

	@DisplayName("사용자가 퀘스트 설정 값을 변경한다.")
	@Test
	void update_quest_property() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + token)
			.body(UpdateQuestPropertyRequest.of(1, 1, 1))
			.when()
			.put(QUEST_PROPERTY_URL)
			.then()
			.statusCode(NO_CONTENT.value());
	}

	@DisplayName("사용자가 잘못된 수치로 퀘스트 설정 값을 변경한다.")
	@Test
	void update_quest_property_with_invalid_property() throws JsonProcessingException {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + token)
			.body(UpdateQuestPropertyRequest.of(-1, 5, 5))
			.when()
			.put(QUEST_PROPERTY_URL)
			.then()
			.statusCode(UNPROCESSABLE_ENTITY.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo(UNPROCESSABLE_ENTITY.value()))
			.body("code", equalTo(INVALID_PARAMETER.name()))
			.body("message", equalTo("Invalid threshold range"))
			.body("path", equalTo(QUEST_PROPERTY_URL));
	}
}
