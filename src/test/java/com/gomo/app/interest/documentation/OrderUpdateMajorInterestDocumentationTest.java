package com.gomo.app.interest.documentation;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.interest.common.util.MajorInterestDataHelper;
import com.gomo.app.interest.documentation.snippet.OrderUpdateMajorInterestSnippet;
import com.gomo.app.interest.presentation.request.OrderUpdateMajorInterestRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@DisplayName("[Presentation documentation]: 주요 관심사 정렬 순서 변경 테스트")
public class OrderUpdateMajorInterestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = OrderUpdateMajorInterestSnippet.create();

	@Autowired
	private MajorInterestDataHelper majorInterestDataHelper;

	@AfterEach
	void tearDown() {
		majorInterestDataHelper.cleanUp();
	}

	@DisplayName("사용자가 주요 관심사의 정렬 순서를 변경한다.")
	@Test
	void update_major_interest_order() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + token)
			.body(OrderUpdateMajorInterestRequest.of(List.of(2, 1)))
			.when()
			.put("/interests/majors/orders")
			.then()
			.statusCode(NO_CONTENT.value());
	}
}
