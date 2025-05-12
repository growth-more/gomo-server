package com.gomo.app.member.documentation;

import static io.restassured.RestAssured.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.member.documentation.snippet.DeleteProfileBannerSnippet;

@DisplayName("[Presentation Documentation]: 프로필 배너 삭제 테스트")
public class DeleteProfileBannerDocumentationTest extends DocumentationTestBase {

	private static final String DELETE_BANNER_URL = "/members/images/banners";

	private final RestDocumentationFilter filter = DeleteProfileBannerSnippet.create();
	private final RestDocumentationFilter errorFilter = DeleteProfileBannerSnippet.createError();

	@DisplayName("사용자가 프로필 배너를 삭제한다.")
	@Test
	void update_profile_banner() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.when()
			.delete(DELETE_BANNER_URL)
			.then()
			.statusCode(OK.value());
	}
}
