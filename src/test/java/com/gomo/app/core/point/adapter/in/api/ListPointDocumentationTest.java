package com.gomo.app.core.point.adapter.in.api;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.core.point.adapter.in.api.snippet.ListPointSnippet;
import com.gomo.app.core.point.domain.model.Point;
import com.gomo.app.core.point.domain.repository.PointRepository;
import com.gomo.app.core.point.fixture.PointFixture;
import com.gomo.app.test.DocumentationTestBase;

@DisplayName("[Presentation documentation]: 포인트 목록 조회 테스트")
public class ListPointDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = ListPointSnippet.create();

	@Autowired
	private PointRepository pointRepository;

	@AfterEach
	void tearDown() {
		pointRepository.deleteAllInBatch();
	}

	@DisplayName("사용자가 포인트 목록을 조회한다.")
	@Test
	void history_point() {
		Point point1 = PointFixture.create(UUIDGenerator.generate(), sessionMemberId);
		Point point2 = PointFixture.create(UUIDGenerator.generate(), sessionMemberId);
		Point point3 = PointFixture.create(UUIDGenerator.generate(), sessionMemberId);
		pointRepository.saveAll(List.of(point1, point2, point3));

		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
			.param("size", 10)
			.when()
			.get("/points")
			.then()
			.statusCode(OK.value())
			.body("points", hasSize(3));
	}
}
