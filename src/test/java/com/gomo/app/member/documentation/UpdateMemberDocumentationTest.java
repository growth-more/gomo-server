package com.gomo.app.member.documentation;

import static io.restassured.RestAssured.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.member.documentation.snippet.UpdateMemberSnippet;
import com.gomo.app.core.member.presentation.request.UpdateMemberRequest;

@DisplayName("[Presentation documentation]: 회원 기본 정보 수정 테스트")
public class UpdateMemberDocumentationTest extends DocumentationTestBase {

	private static final String UPDATE_MEMBER_URL = "/members";
	private final RestDocumentationFilter filter = UpdateMemberSnippet.create();
	private final RestDocumentationFilter errorFilter = UpdateMemberSnippet.createError();

	@DisplayName("이름과 한 줄 다짐을 변경한다.")
	@Test
	void update_member_name_and_motto() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.body(UpdateMemberRequest.of("GOMO4", "GOMOTEST.."))
			.when()
			.put(UPDATE_MEMBER_URL)
			.then()
			.statusCode(NO_CONTENT.value());
	}

}
