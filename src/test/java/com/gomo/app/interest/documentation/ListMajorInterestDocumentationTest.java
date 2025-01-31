package com.gomo.app.interest.documentation;

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
import com.gomo.app.interest.common.dataprovider.MajorInterestDataProvider;
import com.gomo.app.interest.documentation.snippet.ListMajorInterestSnippet;
import com.gomo.app.interest.domain.model.MajorInterest;

@DisplayName("[Presentation documentation]: 주요 관심사 목록 조회 테스트")
public class ListMajorInterestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = ListMajorInterestSnippet.create();

	@Autowired
	private LoginMemberHelper loginHelper;

	@Autowired
	private MajorInterestDataProvider majorInterestDataProvider;
	private MajorInterest java;
	private MajorInterest spring;

	@BeforeEach
	public void setUp() {
		// sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
		java = majorInterestDataProvider.java();
		spring = majorInterestDataProvider.spring();
	}

	@DisplayName("사용자가 주요 관심사 목록을 조회한다.")
	@Test
	void list_major_interest() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.when()
			.get("/interests/majors")
			.then()
			.statusCode(OK.value())
			.body("majorInterests", hasSize(2))
			.body("majorInterests.id", hasItems(java.getId().toString(), spring.getId().toString()));
	}
}
