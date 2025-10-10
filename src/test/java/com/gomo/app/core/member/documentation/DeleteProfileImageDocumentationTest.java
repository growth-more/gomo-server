package com.gomo.app.core.member.documentation;

import static io.restassured.RestAssured.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.core.member.documentation.snippet.DeleteProfileImageSnippet;
import com.gomo.app.test.DocumentationTestBase;

@DisplayName("[Presentation Documentation]: 프로필 이미지 삭제 테스트")
public class DeleteProfileImageDocumentationTest extends DocumentationTestBase {

	private static final String DELETE_PROFILE_URL = "/members/images/profiles";

	private final RestDocumentationFilter filter = DeleteProfileImageSnippet.create();
	private final RestDocumentationFilter errorFilter = DeleteProfileImageSnippet.createError();

	@DisplayName("사용자가 프로필 이미지를 삭제한다.")
	@Test
	void delete_profile_image() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.when()
			.delete(DELETE_PROFILE_URL)
			.then()
			.statusCode(OK.value());
	}
}
