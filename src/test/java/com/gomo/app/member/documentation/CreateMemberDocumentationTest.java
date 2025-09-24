package com.gomo.app.member.documentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.member.documentation.snippet.CreateMemberSnippet;
import com.gomo.app.core.member.domain.model.LoginProvider;
import com.gomo.app.core.member.domain.repository.MemberRepository;
import com.gomo.app.core.member.presentation.request.CreateMemberRequest;

@DisplayName("[Presentation Documentation]: 회원 생성 테스트")
public class CreateMemberDocumentationTest extends DocumentationTestBase {

	private static final String CREATE_MEMBER_URL = "/members";

	private final RestDocumentationFilter filter = CreateMemberSnippet.create();
	private final RestDocumentationFilter errorFilter = CreateMemberSnippet.createError();

	@Autowired
	private MemberRepository memberRepository;

	@AfterEach
	void tearDown() {
		memberRepository.deleteAllInBatch();
	}

	@DisplayName("사용자를 등록한다.")
	@Test
	void create_member() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.body(CreateMemberRequest.of("gomotest3@naver.com", "Test123@", "@GOMOTEST3", "gomotest3", "TEST MOTTO", LoginProvider.EMAIL.name()))
			.when()
			.post(CREATE_MEMBER_URL)
			.then()
			.statusCode(CREATED.value())
			.body("id", hasLength(36));
	}
}
