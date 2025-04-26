package com.gomo.app.member.documentation;

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
import com.gomo.app.member.common.util.MemberDBDataHelper;
import com.gomo.app.member.documentation.snippet.UpdateProfileBannerSnippet;

@DisplayName("[Presentation documentation]: 프로필 배너 변경 테스트")
public class UpdateProfileBannerDocumentationTest extends DocumentationTestBase {

	private static final String URL = "/members/images/banners";

	private final RestDocumentationFilter filter = UpdateProfileBannerSnippet.create();
	private final RestDocumentationFilter errorFilter = UpdateProfileBannerSnippet.createError();

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

	@DisplayName("사용자가 프로필 배너를 변경한다.")
	@Test
	void update_profile_image() throws IOException {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, MULTIPART_FORM_DATA_VALUE)
			.header(AUTHORIZATION, "Bearer " + token)
			.multiPart("profileBanner", getImageFile("normal-image.png"))
			.when()
			.put(URL)
			.then()
			.statusCode(NO_CONTENT.value());
	}

	@DisplayName("사용자가 2MB 보다 큰 이미지로 프로필 이미지를 변경한다.")
	@Test
	void update_profile_image_with_large_image() throws IOException {
		given(this.specification).filter(errorFilter)
			.header(CONTENT_TYPE, MULTIPART_FORM_DATA_VALUE)
			.header(AUTHORIZATION, "Bearer " + token)
			.multiPart("profileBanner", getImageFile("large-image.png"))
			.when()
			.put(URL)
			.then()
			.statusCode(PAYLOAD_TOO_LARGE.value())
			.body("timestamp", instanceOf(String.class))
			.body("path", equalTo(URL))
			.body("httpStatus", equalTo(PAYLOAD_TOO_LARGE.value()))
			.body("code", equalTo("IMA_ROO_001"))
			.body("message", equalTo("Maximum upload size exceeded"));
	}

	private static File getImageFile(String imageName) throws IOException {
		return ResourceUtils.getFile("classpath:image/" + imageName);
	}
}
