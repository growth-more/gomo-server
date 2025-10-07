package com.gomo.app.core.point.presentation.documentation;

import static io.restassured.RestAssured.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.core.point.presentation.documentation.snippet.ReadBalanceSnippet;

@DisplayName("[Presentation documentation]: 포인트 잔고 조회 테스트")
public class ReadBalanceDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = ReadBalanceSnippet.create();

	@DisplayName("사용자가 포인트 잔고를 조회한다.")
	@Test
	void history_point() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
			.when()
			.get("/points/balances")
			.then()
			.statusCode(OK.value());
	}
}
