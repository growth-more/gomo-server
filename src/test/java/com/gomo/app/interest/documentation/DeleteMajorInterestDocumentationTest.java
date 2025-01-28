package com.gomo.app.interest.documentation;

import static io.restassured.RestAssured.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.common.util.LoginMemberHelper;
import com.gomo.app.interest.common.dataprovider.MajorInterestDataProvider;
import com.gomo.app.interest.common.util.MajorInterestDataHelper;
import com.gomo.app.interest.documentation.snippet.DeleteMajorInterestSnippet;
import com.gomo.app.interest.domain.model.MajorInterest;

@DisplayName("[Presentation documentation]: 주요 관심사 삭제 테스트")
public class DeleteMajorInterestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = DeleteMajorInterestSnippet.create();

	@Autowired
	private LoginMemberHelper loginHelper;

	@Autowired
	private MajorInterestDataHelper majorInterestDataHelper;

	@Autowired
	private MajorInterestDataProvider majorInterestDataProvider;
	private MajorInterest java;

	@BeforeEach
	public void setUp() {
		// sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
		java = majorInterestDataProvider.java();
	}

	@AfterEach
	void tearDown() {
		majorInterestDataHelper.cleanUp();
	}

	@DisplayName("사용자가 주요 관심사를 삭제한다.")
	@Test
	void delete_major_interest() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.when()
			.delete("/interests/majors/{id}", java.getId().toString())
			.then()
			.statusCode(NO_CONTENT.value());
	}
}
