package com.gomo.app.interest.documentation;

import static com.gomo.app.common.exception.DomainErrorCode.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.util.ResourceUtils;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.common.util.LoginMemberHelper;
import com.gomo.app.interest.common.util.InterestDataHelper;
import com.gomo.app.interest.documentation.snippet.CreateInterestSnippet;
import com.gomo.app.interest.presentation.request.CreateInterestRequest;

@DisplayName("[Presentation documentation]: 관심사 생성 테스트")
public class CreateInterestDocumentationTest extends DocumentationTestBase {

	private final static String INVALID_INTEREST_NAME = "{}";
	private final static String NORMAL_IMAGE_NAME = "normal-image.png";
	private final static String LARGE_IMAGE_NAME = "large-image.png";

	private final RestDocumentationFilter filter = CreateInterestSnippet.create();
	private final RestDocumentationFilter errorFilter = CreateInterestSnippet.createError();

	@Autowired
	private LoginMemberHelper loginHelper;

	@Autowired
	private InterestDataHelper interestDataHelper;

	@BeforeEach
	public void setUp() {
		// TODO <jhl221123>: 로그인 기능 구현 후, 로그인을 사용하는 문서화 테스트들의 수정이 필요하다.
		// sessionId = loginHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
	}

	@AfterEach
	void tearDown() {
		interestDataHelper.cleanUp();
	}

	@DisplayName("사용자가 관심사를 등록한다.")
	@Test
	void create_interest() throws IOException {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, MULTIPART_FORM_DATA_VALUE)
			.multiPart("request", CreateInterestRequest.of("interest name"), APPLICATION_JSON_VALUE)
			.multiPart("logo", getImageFile(NORMAL_IMAGE_NAME))
			.when()
			.post("/interests")
			.then()
			.statusCode(CREATED.value())
			.body("id", hasLength(36));
	}

	@DisplayName("사용자가 잘못된 이름으로 관심사를 등록한다.")
	@Test
	void create_interest_with_invalid_name() throws IOException {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, MULTIPART_FORM_DATA_VALUE)
			.multiPart("request", CreateInterestRequest.of(INVALID_INTEREST_NAME), APPLICATION_JSON_VALUE)
			.multiPart("logo", getImageFile(NORMAL_IMAGE_NAME))
			.when()
			.post("/interests")
			.then()
			.statusCode(INVALID_PARAMETER.getHttpStatus())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo(INVALID_PARAMETER.getHttpStatus()))
			.body("code", equalTo(INVALID_PARAMETER.name()))
			.body("message", equalTo("Interest name cannot contain forbidden characters"))
			.body("path", equalTo("/interests"));
	}

	@DisplayName("사용자가 크기가 큰 로고 이미지로 관심사를 등록한다.")
	@Test
	void create_interest_with_large_image() throws IOException {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, MULTIPART_FORM_DATA_VALUE)
			.multiPart("request", CreateInterestRequest.of("interest name"), APPLICATION_JSON_VALUE)
			.multiPart("logo", getImageFile(LARGE_IMAGE_NAME))
			.when()
			.post("/interests")
			.then()
			.statusCode(IMAGE_TOO_LARGE.getHttpStatus())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo(IMAGE_TOO_LARGE.getHttpStatus()))
			.body("code", equalTo(IMAGE_TOO_LARGE.name()))
			.body("message", equalTo("Maximum upload size exceeded"))
			.body("path", equalTo("/interests"));
	}

	private static File getImageFile(String imageName) throws IOException {
		return ResourceUtils.getFile("classpath:image/" + imageName);
	}
}
