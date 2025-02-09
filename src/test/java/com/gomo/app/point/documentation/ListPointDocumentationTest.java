package com.gomo.app.point.documentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.common.util.LoginMemberHelper;
import com.gomo.app.point.common.dataprovider.PointDataProvider;
import com.gomo.app.point.documentation.snippet.ListPointSnippet;
import com.gomo.app.point.domain.model.Point;

@DisplayName("[Presentation documentation]: 포인트 목록 조회 테스트")
public class ListPointDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = ListPointSnippet.create();

	@Autowired
	private LoginMemberHelper loginHelper;

	@Autowired
	private PointDataProvider pointDataProvider;
	private Point dailyQuestPoint;
	private Point weeklyQuestPoint;
	private Point monthlyQuestPoint;

	@BeforeEach
	public void setUp() {
		// sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
		dailyQuestPoint = pointDataProvider.dailyQuest();
		weeklyQuestPoint = pointDataProvider.weeklyQuest();
		monthlyQuestPoint = pointDataProvider.monthlyQuest();
	}

	@DisplayName("사용자가 포인트 목록을 조회한다.")
	@Test
	void history_point() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.param("size", 10)
			.when()
			.get("/points")
			.then()
			.statusCode(OK.value())
			.body("points", hasSize(3))
			.body("points.sourceType", everyItem(is(equalTo(dailyQuestPoint.getSourceType().name()))))
			.body("points.transactionType", everyItem(is(equalTo(dailyQuestPoint.getTransactionType().name()))))
			.body("points.amount", containsInAnyOrder(
				dailyQuestPoint.getAmount(),
				weeklyQuestPoint.getAmount(),
				monthlyQuestPoint.getAmount()
			))
			.body("points.description", containsInAnyOrder(
				dailyQuestPoint.getDescription(),
				weeklyQuestPoint.getDescription(),
				monthlyQuestPoint.getDescription()
			))
			.body("points.transactionDateTime", containsInAnyOrder(
				dailyQuestPoint.getTransactionDateTime().toString(),
				weeklyQuestPoint.getTransactionDateTime().toString(),
				monthlyQuestPoint.getTransactionDateTime().toString()
			));
	}
}
