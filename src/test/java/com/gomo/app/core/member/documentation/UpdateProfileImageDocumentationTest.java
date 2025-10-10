package com.gomo.app.core.member.documentation;

import static io.restassured.RestAssured.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import java.io.File;
import java.io.IOException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.util.ResourceUtils;

import com.gomo.app.core.member.documentation.snippet.UpdateProfileImageSnippet;
import com.gomo.app.test.DocumentationTestBase;

@DisplayName("[Presentation Documentation]: 프로필 이미지 수정 테스트")
public class UpdateProfileImageDocumentationTest extends DocumentationTestBase {

	private static final String UPDATE_PROFILE_URL = "/members/images/profiles";

	private final RestDocumentationFilter filter = UpdateProfileImageSnippet.create();
	private final RestDocumentationFilter errorFilter = UpdateProfileImageSnippet.createError();

	@DisplayName("사용자가 프로필 이미지를 변경한다.")
	@Test
	void update_profile_image() throws IOException {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, MULTIPART_FORM_DATA_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.multiPart("profileImage", getImageFile("normal-image.png"))
			.when()
			.put(UPDATE_PROFILE_URL)
			.then()
			.statusCode(NO_CONTENT.value());
	}

	private static File getImageFile(String imageName) throws IOException {
		return ResourceUtils.getFile("classpath:image/" + imageName);
	}
}
