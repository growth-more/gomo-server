package com.gomo.app.core.auth.adapter.in.api;

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

import com.gomo.app.core.auth.adapter.in.api.request.CreateEmailCodeRequest;
import com.gomo.app.core.auth.adapter.in.api.request.CreatePrincipalRequest;
import com.gomo.app.core.auth.adapter.in.api.request.VerifyEmailCodeRequest;
import com.gomo.app.core.auth.adapter.in.api.snippet.SignupSnippet;
import com.gomo.app.core.auth.domain.repository.AuthCodeRepository;
import com.gomo.app.core.member.domain.model.LoginProvider;
import com.gomo.app.core.member.domain.repository.MemberRepository;
import com.gomo.app.test.DocumentationTestBase;

@DisplayName("[Presentation Documentation]: 회원 가입 테스트")
public class SignupDocumentationTest extends DocumentationTestBase {

	private static final String URL = "/auth/signup";

	private final RestDocumentationFilter filter = SignupSnippet.create();
	private final RestDocumentationFilter errorFilter = SignupSnippet.createError();

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

	@DisplayName("회원 가입한다.")
	@Test
	void create_member() {
		String email = "gomotest3@naver.com";
		emailCodeApi.create(CreateEmailCodeRequest.of(email));
		String emailCode = authCodeRepository.findByEmail(email).get();
		String temporaryToken = emailCodeApi.verify(VerifyEmailCodeRequest.of(email, emailCode)).getBody().getTemporaryToken();

		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.body(CreatePrincipalRequest.of(email, "Test123@", "@GOMOTEST3", "gomotest3", "TEST MOTTO", LoginProvider.EMAIL.name(), temporaryToken))
			.when()
			.post(URL)
			.then()
			.statusCode(CREATED.value())
			.body("id", hasLength(36));
	}
}
