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
import com.gomo.app.interest.documentation.snippet.CreateInterestSnippet;
import com.gomo.app.interest.domain.repository.InterestRepository;
import com.gomo.app.interest.exception.code.InterestNameErrorCode;

@DisplayName("[Presentation documentation]: 관심사 생성 테스트")
public class CreateInterestDocumentationTest extends DocumentationTestBase {

	private final static String INVALID_INTEREST_NAME = "{}";
	private final static String NORMAL_IMAGE_NAME = "normal-image.png";
	private final static String LARGE_IMAGE_NAME = "large-image.png";

	private final RestDocumentationFilter filter = CreateInterestSnippet.create();
	private final RestDocumentationFilter errorFilter = CreateInterestSnippet.createError();

	@Autowired
	private InterestRepository interestRepository;

	@AfterEach
	void tearDown() {
		interestRepository.deleteAllInBatch();
	}

	@DisplayName("관심사를 등록한다.")
	@Test
	void create_interest() throws IOException {
		given(this.specification).filter(filter)
			.log().all()
			.header(CONTENT_TYPE, MULTIPART_FORM_DATA_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.multiPart("name", "interest name")
			.multiPart("colorCode", "#0000FF")
			.multiPart("logo", getImageFile(NORMAL_IMAGE_NAME))
			.when()
			.post("/interests")
			.then()
			.statusCode(CREATED.value())
			.body("id", hasLength(36));
	}

	@DisplayName("금지된 문자가 포함된 관심사 이름을 등록한다.")
	@Test
	void create_interest_with_forbidden_name() throws IOException {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, MULTIPART_FORM_DATA_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.multiPart("name", INVALID_INTEREST_NAME)
			.multiPart("colorCode", "#0000FF")
			.multiPart("logo", getImageFile(NORMAL_IMAGE_NAME))
			.when()
			.post("/interests")
			.then()
			.statusCode(InterestNameErrorCode.FORBIDDEN.getHttpStatus())
			.body("timestamp", instanceOf(String.class))
			.body("path", equalTo("/interests"))
			.body("httpStatus", equalTo(InterestNameErrorCode.FORBIDDEN.getHttpStatus()))
			.body("code", equalTo(InterestNameErrorCode.FORBIDDEN.getErrorCode()))
			.body("message", equalTo(InterestNameErrorCode.FORBIDDEN.getMessage()));
	}

	@DisplayName("크기가 큰 로고 이미지로 관심사를 등록한다.")
	@Test
	void create_interest_with_large_image() throws IOException {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, MULTIPART_FORM_DATA_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.multiPart("name", "interest name")
			.multiPart("colorCode", "#0000FF")
			.multiPart("logo", getImageFile(LARGE_IMAGE_NAME))
			.when()
			.post("/interests")
			.then()
			.statusCode(HttpStatus.PAYLOAD_TOO_LARGE.value())
			.body("timestamp", instanceOf(String.class))
			.body("path", equalTo("/interests"))
			.body("httpStatus", equalTo(HttpStatus.PAYLOAD_TOO_LARGE.value()))
			.body("code", equalTo("IMA_ROO_001"))
			.body("message", equalTo("Maximum upload size exceeded"));
	}

	private static File getImageFile(String imageName) throws IOException {
		return ResourceUtils.getFile("classpath:image/" + imageName);
	}
}
