package com.gomo.app.member.documentation;

import static io.restassured.RestAssured.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.member.documentation.snippet.ResetPasswordSnippet;
import com.gomo.app.core.member.domain.repository.MemberRepository;
import com.gomo.app.core.member.presentation.request.ResetPasswordRequest;

@DisplayName("[Presentation Documentation]: 비밀번호 초기화 테스트")
public class ResetPasswordDocumentationTest extends DocumentationTestBase {

	private static final String RESET_PASSWORD_URL = "/members/passwords/reset";

	private final RestDocumentationFilter filter = ResetPasswordSnippet.create();
	private final RestDocumentationFilter errorFilter = ResetPasswordSnippet.createError();

	@Autowired
	private MemberRepository memberRepository;

	@AfterEach
	void tearDown() {
		memberRepository.deleteAllInBatch();
	}

	@DisplayName("비밀번호를 초기화 한다.")
	@Test
	void update_password() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.body(ResetPasswordRequest.of("testmember@naver.com", "Test1234!"))
			.when()
			.put(RESET_PASSWORD_URL)
			.then()
			.statusCode(NO_CONTENT.value());
	}
}
