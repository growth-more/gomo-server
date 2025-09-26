package com.gomo.app.core.member.documentation;

import static io.restassured.RestAssured.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.core.member.application.port.command.CreateMemberCommand;
import com.gomo.app.core.member.application.usecase.CreateMemberUseCase;
import com.gomo.app.core.member.documentation.snippet.DeleteMemberSnippet;
import com.gomo.app.core.member.domain.model.LoginProvider;
import com.gomo.app.support.auth.application.AuthenticateUseCase;

@DisplayName("[Presentation documentation]: 회원 탈퇴 테스트")
public class DeleteMemberDocumentationTest extends DocumentationTestBase {

	private static final String MEMBER_DELETE_URL = "/members";

	private final RestDocumentationFilter filter = DeleteMemberSnippet.create();
	private final RestDocumentationFilter errorFilter = DeleteMemberSnippet.createError();

	@Autowired
	private CreateMemberUseCase createMemberUseCase;

	@Autowired
	private AuthenticateUseCase authenticateUseCase;

	@BeforeEach
	void setUp() {
		String email = "user_delete@test.com";
		String password = "userDelete123@";
		String temporaryToken = UUID.randomUUID().toString();
		CreateMemberCommand command = CreateMemberCommand.of(email, password, "@deletetester", "deleteTester", "MyMotto", LoginProvider.EMAIL.name(), temporaryToken);
		createMemberUseCase.create(command);
		this.accessToken = authenticateUseCase.authenticate(email, password).accessToken();
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
