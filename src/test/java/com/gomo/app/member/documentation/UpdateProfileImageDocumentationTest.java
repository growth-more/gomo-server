package com.gomo.app.member.documentation;

import static com.gomo.app.member.exception.MemberErrorCode.*;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.util.ResourceUtils;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.common.util.LoginMemberHelper;
import com.gomo.app.member.documentation.snippet.UpdateProfileImageSnippet;
import com.gomo.app.member.presentation.request.UpdateProfileImageRequest;
import com.gomo.app.member.common.util.MemberDBDataHelper;

public class UpdateProfileImageDocumentationTest extends DocumentationTestBase {

	private static final String PROFILE_IMAGE_URL = "/members/images/profiles";

	private final RestDocumentationFilter filter = UpdateProfileImageSnippet.create();
	private final RestDocumentationFilter errorFilter = UpdateProfileImageSnippet.createError();

	private String sessionId;

	@Autowired
	LoginMemberHelper loginMemberHelper;

	@Autowired
	MemberDBDataHelper memberDBDataHelper;

	@BeforeEach
	public void setUp() {
		sessionId = loginMemberHelper.getSessionId(TestMemberFixture.email(), TestMemberFixture.password());
	}

	@AfterEach
	public void tearDown() {
		memberDBDataHelper.cleanUp();
	}

	@DisplayName("사용자가 프로필 이미지를 변경한다.")
	@Test
	void update_profile_image() throws IOException {
		given(this.specification).filter(filter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
			.sessionId(sessionId)
			.body(UpdateProfileImageRequest.of(getMultipartFile("normal-image.png")))
			.when()
			.put(PROFILE_IMAGE_URL)
			.then()
			.statusCode(OK.value())
			.body("profileImageUrl", equalTo("changedUrl")) // TODO <jhl221123>: 추후 이미지 저장소 경로로 수정 필요
			.body("profileImageName", equalTo("normal-image"));
	}

	@DisplayName("사용자가 2MB 보다 큰 이미지로 프로필 이미지를 변경한다.")
	@Test
	void update_profile_image_with_large_image() throws IOException {
		given(this.specification).filter(errorFilter)
			.header(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE)
			.sessionId(sessionId)
			.body(UpdateProfileImageRequest.of(getMultipartFile("large-image.png")))
			.when()
			.put(PROFILE_IMAGE_URL)
			.then()
			.statusCode(HttpStatus.PAYLOAD_TOO_LARGE.value())
			.body("timestamp", instanceOf(String.class))
			.body("httpStatus", equalTo("413"))
			.body("code", equalTo(PROFILE_IMAGE_TOO_LARGE.name()))
			.body("message", equalTo("Profile image size too large"))
			.body("path", equalTo(PROFILE_IMAGE_URL));
	}

	private static @NotNull MockMultipartFile getMultipartFile(String imageName) throws IOException {
		File file = ResourceUtils.getFile("classpath:resource/image/" + imageName);
		FileInputStream input = new FileInputStream(file);
		return new MockMultipartFile(imageName, file.getName(), "image/png", input);
	}
}
