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
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.presentation.request.CreateMemberRequest;

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

	private static final String EMAIL = "gomotest3@naver.com";
	private static final String PASSWORD = "Test123@";
	private static final String HANDLE = "@GOMOTEST3";
	private static final String NAME = "gomotest3";
	private static final String MOTTO = "TEST MOTTO";

	@DisplayName("사용자를 등록한다.")
	@Test
	void create_member() {
		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.body(CreateMemberRequest.of(EMAIL, PASSWORD, HANDLE, NAME, MOTTO))
			.when()
			.post(CREATE_MEMBER_URL)
			.then()
			.statusCode(CREATED.value())
			.body("id", hasLength(36));
	}

}
