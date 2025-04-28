package com.gomo.app.interest.documentation;

import static io.restassured.RestAssured.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import java.util.List;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.interest.common.dataprovider.MajorInterestDataProvider;
import com.gomo.app.interest.common.util.MajorInterestDataHelper;
import com.gomo.app.interest.documentation.snippet.OrderUpdateMajorInterestSnippet;
import com.gomo.app.interest.domain.model.MajorInterest;
import com.gomo.app.interest.presentation.request.OrderUpdateMajorInterestRequest;
import com.gomo.app.interest.presentation.request.UpdateOrderRequest;

@DisplayName("[Presentation documentation]: 주요 관심사 정렬 순서 변경 테스트")
public class OrderUpdateMajorInterestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = OrderUpdateMajorInterestSnippet.create();

	@Autowired
	private MajorInterestDataHelper majorInterestDataHelper;

	@Autowired
	private MajorInterestDataProvider majorInterestDataProvider;
	private MajorInterest majorInterestA;
	private MajorInterest majorInterestB;

	@BeforeEach
	void setup() {
		majorInterestA = majorInterestDataProvider.java();
		majorInterestB = majorInterestDataProvider.spring();
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
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.body(getRequest())
			.when()
			.put("/interests/majors/orders")
			.then()
			.statusCode(NO_CONTENT.value());
	}

	private @NotNull OrderUpdateMajorInterestRequest getRequest() {
		return OrderUpdateMajorInterestRequest.of(
			List.of(
				UpdateOrderRequest.of(majorInterestA.uuid(), 2),
				UpdateOrderRequest.of(majorInterestB.uuid(), 1)
			)
		);
	}
}
