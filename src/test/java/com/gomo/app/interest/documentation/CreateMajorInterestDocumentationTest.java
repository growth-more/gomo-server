package com.gomo.app.interest.documentation;

import static com.gomo.app.interest.exception.MajorInterestErrorCode.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
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
import com.gomo.app.interest.common.dataprovider.InterestDataProvider;
import com.gomo.app.interest.common.util.InterestDataHelper;
import com.gomo.app.interest.common.util.MajorInterestDataHelper;
import com.gomo.app.interest.documentation.snippet.CreateMajorInterestSnippet;
import com.gomo.app.interest.domain.model.Interest;

@DisplayName("[Presentation documentation]: 주요 관심사 생성 테스트")
public class CreateMajorInterestDocumentationTest extends DocumentationTestBase {

	private final RestDocumentationFilter filter = CreateMajorInterestSnippet.create();
	private final RestDocumentationFilter errorFilter = CreateMajorInterestSnippet.createError();

	@Autowired
	private LoginMemberHelper loginHelper;

	@Autowired
	private MajorInterestDataHelper majorInterestDataHelper;

	@Autowired
	private InterestDataHelper interestDataHelper;

	@Autowired
	private InterestDataProvider interestDataProvider;
	private Interest backend;
	private Interest java;

	@BeforeEach
	public void setUp() {
		// sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
		backend = interestDataProvider.backend();
		java = interestDataProvider.java();
	}

	@AfterEach
	void tearDown() {
		majorInterestDataHelper.cleanUp();
		interestDataHelper.cleanUp();
	}

	@DisplayName("사용자가 주요 관심사를 등록한다.")
	@Test
	void create_major_interest() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.when()
			.post("/interests/{id}/majors", backend.getId().getId())
			.then()
			.statusCode(CREATED.value())
			.body("id", hasLength(36));
	}

	@DisplayName("사용자가 이미 주요 관심사로 등록된 관심사로 등록한다.")
	@Test
	void create_already_major_interest() {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.when()
			.post("/interests/{id}/majors", java.getId().getId())
			.then()
			.statusCode(DUPLICATED.getHttpStatus())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo(DUPLICATED.getHttpStatus()))
			.body("code", equalTo(DUPLICATED.name()))
			.body("message", equalTo("Already registered as a major interest"))
			.body("path", equalTo("/interests/" + java.getId().getId() + "/majors"));
	}
}
