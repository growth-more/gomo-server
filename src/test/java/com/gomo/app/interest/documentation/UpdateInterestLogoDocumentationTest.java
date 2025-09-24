package com.gomo.app.interest.documentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.util.ResourceUtils;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.interest.documentation.snippet.UpdateInterestLogoSnippet;
import com.gomo.app.core.interest.domain.repository.InterestRepository;
import com.gomo.app.core.interest.presentation.InterestApi;
import com.gomo.app.core.interest.presentation.request.CreateInterestRequest;

@DisplayName("[Presentation documentation]: 관심사 로고 수정 테스트")
public class UpdateInterestLogoDocumentationTest extends DocumentationTestBase {

	private final static String NORMAL_IMAGE_NAME = "normal-image.png";
	private final static String LARGE_IMAGE_NAME = "large-image.png";

	private final RestDocumentationFilter filter = UpdateInterestLogoSnippet.create();
	private final RestDocumentationFilter errorFilter = UpdateInterestLogoSnippet.createError();

	@Autowired
	private InterestApi interestApi;

	@Autowired
	private InterestRepository interestRepository;

	private UUID interestId;

	@BeforeEach
	public void setUp() {
		interestId = createInterest("interest");
	}

	@AfterEach
	void tearDown() {
		interestRepository.deleteAllInBatch();
	}

	@DisplayName("사용자가 관심사 로고 이미지를 수정한다.")
	@Test
	void update_interest_logo() throws IOException {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, MULTIPART_FORM_DATA_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.multiPart("updatedLogo", getImageFile(NORMAL_IMAGE_NAME))
			.when()
			.put("/interests/{id}/logos", interestId)
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
			.put("/interests/{id}/logos", interestId)
			.then()
			.statusCode(HttpStatus.PAYLOAD_TOO_LARGE.value())
			.body("timestamp", instanceOf(String.class))
			.body("path", equalTo("/interests/" + interestId + "/logos"))
			.body("httpStatus", equalTo(HttpStatus.PAYLOAD_TOO_LARGE.value()))
			.body("code", equalTo("IMA-ROO-001"))
			.body("message", equalTo("Maximum upload size exceeded"));
	}

	private static File getImageFile(String imageName) throws IOException {
		return ResourceUtils.getFile("classpath:image/" + imageName);
	}

	private UUID createInterest(String name) {
		return interestApi.create(super.authInfo, CreateInterestRequest.of(name, "#FF0000", null)).getBody().getId();
	}
}
