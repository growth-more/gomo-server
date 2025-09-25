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

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.core.member.documentation.snippet.UpdateProfileBannerSnippet;

@DisplayName("[Presentation Documentation]: 프로필 배너 변경 테스트")
public class UpdateProfileBannerDocumentationTest extends DocumentationTestBase {

	private static final String UPDATE_BANNER_URL = "/members/images/banners";

	private final RestDocumentationFilter filter = UpdateProfileBannerSnippet.create();
	private final RestDocumentationFilter errorFilter = UpdateProfileBannerSnippet.createError();

	@DisplayName("사용자가 프로필 배너를 변경한다.")
	@Test
	void update_profile_banner() throws IOException {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, MULTIPART_FORM_DATA_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.multiPart("profileBanner", getImageFile("normal-image.png"))
			.when()
			.put(UPDATE_BANNER_URL)
			.then()
			.statusCode(NO_CONTENT.value());
	}

	private static File getImageFile(String imageName) throws IOException {
		return ResourceUtils.getFile("classpath:image/" + imageName);
	}
}
