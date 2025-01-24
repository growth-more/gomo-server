package com.gomo.app.point.documentation;

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
import com.gomo.app.point.documentation.snippet.HistoryPointSnippet;

public class HistoryPointDocumentationTest extends DocumentationTestBase {

	private static final String HISTORY_POINT_URL = "/points/histories";

	private final RestDocumentationFilter filter = HistoryPointSnippet.create();

	@Autowired
	private LoginMemberHelper loginHelper;

	@BeforeEach
	public void setUp() {
		sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
	}

	@DisplayName("사용자가 포인트 이력을 조회한다.")
	@Test
	void history_point() {
		given(this.specification).filter(filter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.param("size", 1)
			.param("lastElementId", "")
			.when()
			.get(HISTORY_POINT_URL)
			.then()
			.statusCode(OK.value())
			.body("histories", hasSize(3))
			.body("histories.points", hasItems(
				10, 150, 1500
			));
	}
}
