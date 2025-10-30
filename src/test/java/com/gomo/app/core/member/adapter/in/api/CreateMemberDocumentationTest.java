package com.gomo.app.core.member.adapter.in.api;

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

import com.gomo.app.core.member.adapter.in.api.request.CreateEmailCodeRequest;
import com.gomo.app.core.member.adapter.in.api.request.CreateMemberRequest;
import com.gomo.app.core.member.adapter.in.api.request.VerifyEmailCodeRequest;
import com.gomo.app.core.member.adapter.in.api.snippet.CreateMemberSnippet;
import com.gomo.app.core.member.domain.model.LoginProvider;
import com.gomo.app.core.member.domain.repository.MemberRepository;
import com.gomo.app.support.auth.domain.repository.AuthCodeRepository;
import com.gomo.app.test.DocumentationTestBase;

@DisplayName("[Presentation Documentation]: 회원 생성 테스트")
public class CreateMemberDocumentationTest extends DocumentationTestBase {

	private static final String CREATE_MEMBER_URL = "/members";

	private final RestDocumentationFilter filter = CreateMemberSnippet.create();
	private final RestDocumentationFilter errorFilter = CreateMemberSnippet.createError();

	@Autowired
	private EmailCodeApi emailCodeApi;

	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private AuthCodeRepository authCodeRepository;

	@AfterEach
	void tearDown() {
		memberRepository.deleteAllInBatch();
	}

	@DisplayName("사용자를 등록한다.")
	@Test
	void create_member() {
		String email = "gomotest3@naver.com";
		emailCodeApi.create(CreateEmailCodeRequest.of(email));
		String emailCode = authCodeRepository.findByEmail(email).get();
		String temporaryToken = emailCodeApi.verify(VerifyEmailCodeRequest.of(email, emailCode)).getBody().getTemporaryToken();

		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.body(CreateMemberRequest.of(email, "Test123@", "@GOMOTEST3", "gomotest3", "TEST MOTTO", LoginProvider.EMAIL.name(), temporaryToken))
			.when()
			.post(CREATE_MEMBER_URL)
			.then()
			.statusCode(CREATED.value())
			.body("id", hasLength(36));
	}
}
