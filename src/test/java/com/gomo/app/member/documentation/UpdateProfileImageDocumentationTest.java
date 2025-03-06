package com.gomo.app.member.documentation;

import static com.gomo.app.common.exception.DomainErrorCode.IMAGE_TOO_LARGE;
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
import com.gomo.app.member.documentation.snippet.UpdateProfileImageSnippet;
import com.gomo.app.member.common.util.MemberDBDataHelper;

@DisplayName("[Presentation documentation]: 프로필 이미지 변경 테스트")
public class UpdateProfileImageDocumentationTest extends DocumentationTestBase {

	private static final String PROFILE_IMAGE_URL = "/members/images/profiles";

	private final RestDocumentationFilter filter = UpdateProfileImageSnippet.create();
	private final RestDocumentationFilter errorFilter = UpdateProfileImageSnippet.createError();

	private static final String EMAIL = "gomotest@naver.com";
	private static final String PASSWORD = "Gomotest1234@";

	private String token;

	@Autowired
	LoginMemberHelper loginMemberHelper;

	@Autowired
	MemberDBDataHelper memberDBDataHelper;

	@BeforeEach
	public void setUp() {
		token = loginMemberHelper.getAccessToken(EMAIL, PASSWORD);
	}

	@AfterEach
	public void tearDown() {
		memberDBDataHelper.cleanUp();
	}

	@DisplayName("사용자가 프로필 이미지를 변경한다.")
	@Test
	void update_profile_image() throws IOException {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, MULTIPART_FORM_DATA_VALUE)
			.header(AUTHORIZATION, "Bearer " + token)
			.multiPart("profileImage", getImageFile("normal-image.png"))
			.when()
			.put(PROFILE_IMAGE_URL)
			.then()
			.statusCode(NO_CONTENT.value());
	}

	@DisplayName("사용자가 2MB 보다 큰 이미지로 프로필 이미지를 변경한다.")
	@Test
	void update_profile_image_with_large_image() throws IOException {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, MULTIPART_FORM_DATA_VALUE)
			.header(AUTHORIZATION, "Bearer " + token)
			.multiPart("profileImage", getImageFile("large-image.png"))
			.when()
			.put(PROFILE_IMAGE_URL)
			.then()
			.statusCode(IMAGE_TOO_LARGE.getHttpStatus())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo(IMAGE_TOO_LARGE.getHttpStatus()))
			.body("code", equalTo(IMAGE_TOO_LARGE.name()))
			.body("message", equalTo("Maximum upload size exceeded"))
			.body("path", equalTo(PROFILE_IMAGE_URL));
	}

	private static File getImageFile(String imageName) throws IOException {
		return ResourceUtils.getFile("classpath:image/" + imageName);
	}
}
