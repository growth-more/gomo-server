package com.gomo.app.core.member.documentation;

import static io.restassured.RestAssured.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.core.member.documentation.snippet.DeleteMemberSnippet;
import com.gomo.app.test.DocumentationTestBase;

@DisplayName("[Presentation documentation]: 회원 탈퇴 테스트")
public class DeleteMemberDocumentationTest extends DocumentationTestBase {

	private static final String MEMBER_DELETE_URL = "/members";

	private final RestDocumentationFilter filter = DeleteMemberSnippet.create();
	private final RestDocumentationFilter errorFilter = DeleteMemberSnippet.createError();

	@BeforeEach
	void setSessionToTestPrincipal() {
		String email = "test@naver.com";
		String password = "Delete123@";
		signup(email, password, "@DelMemDocTest");
		sessionInit(login(email, password));
	}

	@AfterEach
	void setSessionToOriginalPrincipal() {
		sessionInit(login(sessionEmail, sessionPassword));
	}

	@DisplayName("사용자가 회원 탈퇴를 요청한다.")
	@Test
	void delete_member() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.header(AUTHORIZATION, "Bearer " + accessToken)
			.when()
			.delete(MEMBER_DELETE_URL)
			.then()
			.statusCode(NO_CONTENT.value());
	}
}
