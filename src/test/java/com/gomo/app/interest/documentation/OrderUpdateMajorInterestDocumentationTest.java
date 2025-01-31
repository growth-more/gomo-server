package com.gomo.app.interest.documentation;

import static io.restassured.RestAssured.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.common.util.LoginMemberHelper;
import com.gomo.app.interest.common.util.MajorInterestDataHelper;
import com.gomo.app.interest.documentation.snippet.OrderUpdateMajorInterestSnippet;
import com.gomo.app.interest.presentation.request.OrderUpdateMajorInterestRequest;

@DisplayName("[Presentation documentation]: 주요 관심사 정렬 순서 변경 테스트")
public class OrderUpdateMajorInterestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = OrderUpdateMajorInterestSnippet.create();

	@Autowired
	private LoginMemberHelper loginHelper;

	@Autowired
	private MajorInterestDataHelper majorInterestDataHelper;

	@BeforeEach
	public void setUp() {
		// sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
	}

	@AfterEach
	void tearDown() {
		majorInterestDataHelper.cleanUp();
	}

	@DisplayName("사용자가 주요 관심사의 정렬 순서를 변경한다.")
	@Test
	void update_major_interest_order() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.body(OrderUpdateMajorInterestRequest.of(List.of(2, 1)))
			.when()
			.put("/interests/majors/orders")
			.then()
			.statusCode(NO_CONTENT.value());
	}
}
