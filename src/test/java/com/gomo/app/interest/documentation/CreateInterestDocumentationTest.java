package com.gomo.app.interest.documentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpStatus.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.util.ResourceUtils;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.common.fixture.TestMemberFixture;
import com.gomo.app.common.util.LoginMemberHelper;
import com.gomo.app.interest.common.constant.NonExistInterestField;
import com.gomo.app.interest.common.util.InterestDBDataHelper;
import com.gomo.app.interest.documentation.snippet.CreateInterestSnippet;
import com.gomo.app.interest.exception.InterestErrorCode;
import com.gomo.app.interest.presentation.request.CreateInterestRequest;

public class CreateInterestDocumentationTest extends DocumentationTestBase {

	private static final String INTEREST_URL = "/interests";
	private final static String INVALID_INTEREST_NAME = "# !";

	private final RestDocumentationFilter filter = CreateInterestSnippet.create();
	private final RestDocumentationFilter errorFilter = CreateInterestSnippet.createError();

	@Autowired
	private LoginMemberHelper loginHelper;

	@Autowired
	private InterestDBDataHelper interestDBDataHelper;

	@BeforeEach
	public void setUp() {
		sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
	}

	@AfterEach
	void tearDown() {
		interestDBDataHelper.cleanUp();
	}

	@DisplayName("사용자가 관심사를 등록한다.")
	@Test
	void create_interest() throws IOException {
		given(this.specification).filter(filter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
			.body(CreateInterestRequest.of(NonExistInterestField.NAME, getMultipartFile("normal-image.png")))
			.when()
			.post(INTEREST_URL)
			.then()
			.statusCode(CREATED.value())
			.body("id", hasLength(36));
	}

	@DisplayName("사용자가 잘못된 이름으로 관심사를 등록한다.")
	@Test
	void create_interest_with_invalid_name() throws IOException {
		given(this.specification).filter(errorFilter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
			.body(CreateInterestRequest.of(INVALID_INTEREST_NAME, getMultipartFile("normal-image.png")))
			.when()
			.post(INTEREST_URL)
			.then()
			.statusCode(UNPROCESSABLE_ENTITY.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo("422"))
			.body("code", equalTo(InterestErrorCode.INVALID_PARAMETER.name()))
			.body("message", equalTo("Invalid parameter: " + INVALID_INTEREST_NAME))
			.body("path", equalTo(INTEREST_URL));
	}

	@DisplayName("사용자가 크기가 큰 로고 이미지로 관심사를 등록한다.")
	@Test
	void create_interest_with_large_image() throws IOException {
		given(this.specification).filter(errorFilter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
			.body(CreateInterestRequest.of(NonExistInterestField.NAME, getMultipartFile("large-image.png")))
			.when()
			.post(INTEREST_URL)
			.then()
			.statusCode(PAYLOAD_TOO_LARGE.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo("413"))
			.body("code", equalTo(InterestErrorCode.LOGO_IMAGE_TOO_LARGE.name()))
			.body("message", equalTo("Logo image size too large"))
			.body("path", equalTo(INTEREST_URL));
	}

	private static @NotNull MockMultipartFile getMultipartFile(String imageName) throws IOException {
		File file = ResourceUtils.getFile("classpath:resource/image/" + imageName);
		FileInputStream input = new FileInputStream(file);
		return new MockMultipartFile(imageName, file.getName(), "image/png", input);
	}
}
