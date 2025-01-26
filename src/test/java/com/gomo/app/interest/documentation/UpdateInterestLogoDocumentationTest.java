package com.gomo.app.interest.documentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.util.ResourceUtils;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.common.fixture.TestMemberFixture;
import com.gomo.app.common.util.LoginMemberHelper;
import com.gomo.app.interest.common.util.InterestDataHelper;
import com.gomo.app.interest.documentation.snippet.UpdateInterestSnippet;
import com.gomo.app.interest.exception.InterestErrorCode;

public class UpdateInterestLogoDocumentationTest extends DocumentationTestBase {

	private static final String INTEREST_URL = "/interests/{id}/logos";
	private final static String NORMAL_IMAGE_NAME = "normal-image.png";
	private final static String LARGE_IMAGE_NAME = "large-image.png";

	private final RestDocumentationFilter filter = UpdateInterestSnippet.create();
	private final RestDocumentationFilter errorFilter = UpdateInterestSnippet.createError();

	@Autowired
	private LoginMemberHelper loginHelper;

	@Autowired
	private InterestDataHelper interestDataHelper;

	@BeforeEach
	public void setUp() {
		sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
	}

	@AfterEach
	void tearDown() {
		interestDataHelper.cleanUp();
	}

	@DisplayName("사용자가 관심사 로고 이미지를 수정한다.")
	@Test
	void update_interest_logo() throws IOException {
		given(this.specification).filter(filter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
			.multiPart("logo", getImageFile(NORMAL_IMAGE_NAME))
			.when()
			.put(INTEREST_URL)
			.then()
			.statusCode(NO_CONTENT.value());
	}

	@DisplayName("사용자가 너무 큰 이미지로 로고 이미지를 수정한다.")
	@Test
	void update_interest_logo_with_large_image() throws IOException {
		given(this.specification).filter(errorFilter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
			.multiPart("logo", getImageFile(LARGE_IMAGE_NAME))
			.when()
			.put(INTEREST_URL)
			.then()
			.statusCode(PAYLOAD_TOO_LARGE.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo("413"))
			.body("code", equalTo(InterestErrorCode.LOGO_IMAGE_TOO_LARGE.name()))
			.body("message", equalTo("Logo image size too large"))
			.body("path", equalTo(INTEREST_URL));
	}

	private static File getImageFile(String imageName) throws IOException {
		return ResourceUtils.getFile("classpath:image/" + imageName);
	}
}
