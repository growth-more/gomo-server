package com.gomo.app.member.documentation;

import static io.restassured.RestAssured.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.auth.application.LoginMemberUseCase;
import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.member.application.CreateMemberUseCase;
import com.gomo.app.member.application.port.command.CreateMemberCommand;
import com.gomo.app.member.documentation.snippet.DeleteMemberSnippet;
import com.gomo.app.member.domain.model.LoginProvider;

@DisplayName("[Presentation documentation]: 회원 탈퇴 테스트")
public class DeleteMemberDocumentationTest extends DocumentationTestBase {

	private static final String MEMBER_DELETE_URL = "/members";

	private final RestDocumentationFilter filter = DeleteMemberSnippet.create();
	private final RestDocumentationFilter errorFilter = DeleteMemberSnippet.createError();

	@Autowired
	private CreateMemberUseCase createMemberUseCase;

	@Autowired
	private LoginMemberUseCase loginMemberUseCase;

	@BeforeEach
	void setUp() {
		String email = "user_delete@test.com";
		String password = "userDelete123@";
		CreateMemberCommand command = CreateMemberCommand.of(email, password, "@deletetester", "deleteTester", "MyMotto", LoginProvider.EMAIL.name());
		createMemberUseCase.create(command);
		this.accessToken = loginMemberUseCase.login(email, password).getAuthToken().getAccessToken();
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
