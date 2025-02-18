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
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.common.util.LoginMemberHelper;
import com.gomo.app.quest.common.dataprovider.RepeatQuestDataProvider;
import com.gomo.app.quest.common.util.RepeatQuestDataHelper;
import com.gomo.app.quest.documentation.snippet.DeleteRepeatQuestSnippet;
import com.gomo.app.quest.domain.model.RepeatQuest;

@DisplayName("[Presentation documentation]: 반복 퀘스트 삭제 테스트")
public class DeleteRepeatQuestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = DeleteRepeatQuestSnippet.create();

	@Autowired
	private RepeatQuestDataHelper repeatQuestDataHelper;

	@Autowired
	private RepeatQuestDataProvider repeatQuestDataProvider;
	private RepeatQuest repeatQuest;

	@BeforeEach
	public void setUp() {
		repeatQuest = repeatQuestDataProvider.firstOrderDaily();
	}

	@AfterEach
	void tearDown() {
		repeatQuestDataHelper.cleanUp();
	}

	@DisplayName("사용자가 반복 퀘스트를 삭제한다.")
	@Test
	void delete_repeat_quest() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + token)
			.when()
			.delete("/quests/repeats/{id}", repeatQuest.getId().getId())
			.then()
			.statusCode(NO_CONTENT.value());
	}
}
