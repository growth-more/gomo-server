package com.gomo.app.interest.documentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.util.ResourceUtils;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.interest.common.util.InterestDataHelper;
import com.gomo.app.interest.documentation.snippet.UpdateInterestLogoSnippet;

@DisplayName("[Presentation documentation]: 관심사 로고 수정 테스트")
public class UpdateInterestLogoDocumentationTest extends DocumentationTestBase {

	private static final String UPDATED_INTEREST_ID = "3bd1b3f7-d7c6-11ef-abb8-a7e09b2a499c";
	private final static String NORMAL_IMAGE_NAME = "normal-image.png";
	private final static String LARGE_IMAGE_NAME = "large-image.png";

	private final RestDocumentationFilter filter = UpdateInterestLogoSnippet.create();
	private final RestDocumentationFilter errorFilter = UpdateInterestLogoSnippet.createError();

	@Autowired
	private InterestDataHelper interestDataHelper;

	@AfterEach
	void tearDown() {
		interestDataHelper.cleanUp();
	}

	@DisplayName("사용자가 관심사 로고 이미지를 수정한다.")
	@Test
	void update_interest_logo() throws IOException {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, MULTIPART_FORM_DATA_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.multiPart("updatedLogo", getImageFile(NORMAL_IMAGE_NAME))
			.when()
			.put("/interests/{id}/logos", UPDATED_INTEREST_ID)
			.then()
			.statusCode(NO_CONTENT.value());
	}

	@DisplayName("사용자가 너무 큰 이미지로 로고 이미지를 수정한다.")
	@Test
	void update_interest_logo_with_large_image() throws IOException {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, MULTIPART_FORM_DATA_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.multiPart("updatedLogo", getImageFile(LARGE_IMAGE_NAME))
			.when()
			.put("/interests/{id}/logos", UPDATED_INTEREST_ID)
			.then()
			.statusCode(HttpStatus.PAYLOAD_TOO_LARGE.value())
			.body("timestamp", instanceOf(String.class))
			.body("path", equalTo("/interests/" + UPDATED_INTEREST_ID + "/logos"))
			.body("httpStatus", equalTo(HttpStatus.PAYLOAD_TOO_LARGE.value()))
			.body("code", equalTo("IMA_ROO_001"))
			.body("message", equalTo("Maximum upload size exceeded"));
	}

	private static File getImageFile(String imageName) throws IOException {
		return ResourceUtils.getFile("classpath:image/" + imageName);
	}
}
