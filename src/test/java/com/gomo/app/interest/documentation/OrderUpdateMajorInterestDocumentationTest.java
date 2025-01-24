package com.gomo.app.interest.documentation;

import static io.restassured.RestAssured.*;
import static org.springframework.http.HttpStatus.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
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
import com.gomo.app.interest.common.fixture.majorinterest.JavaMajorInterestFixture;
import com.gomo.app.interest.common.fixture.majorinterest.SpringMajorInterestFixture;
import com.gomo.app.interest.common.util.MajorInterestDBDataHelper;
import com.gomo.app.interest.documentation.snippet.OrderUpdateMajorInterestSnippet;
import com.gomo.app.interest.presentation.request.OrderUpdateMajorInterestRequest;

public class OrderUpdateMajorInterestDocumentationTest extends DocumentationTestBase {

	private static final String ORDER_UPDATE_MAJOR_INTEREST_URL = "/interests/majors/orders";

	private final RestDocumentationFilter filter = OrderUpdateMajorInterestSnippet.create();

	@Autowired
	private LoginMemberHelper loginHelper;

	@Autowired
	private MajorInterestDBDataHelper majorInterestDBDataHelper;

	@BeforeEach
	public void setUp() {
		sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
	}

	@AfterEach
	void tearDown() {
		majorInterestDBDataHelper.cleanUp();
	}

	@DisplayName("사용자가 주요 관심사의 정렬 순서를 변경한다.")
	@Test
	void update_major_interest_order() {
		given(this.specification).filter(filter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.body(OrderUpdateMajorInterestRequest.of(List.of(SpringMajorInterestFixture.displayOrder(), JavaMajorInterestFixture.displayOrder())))
			.when()
			.put(ORDER_UPDATE_MAJOR_INTEREST_URL)
			.then()
			.statusCode(NO_CONTENT.value());
	}
}
