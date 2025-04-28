package com.gomo.app.member.documentation;

import static io.restassured.RestAssured.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.member.application.OAuthUseCase;
import com.gomo.app.member.documentation.snippet.OAuthSnippet;
import com.gomo.app.member.presentation.response.LoginMemberResponse;

@DisplayName("[Presentation documentation]: OAuth 로그인 테스트")
public class OAuthDocumentationTest extends DocumentationTestBase {

	private static final String OAUTH_GOOGLE_URL = "/oauth/login/google";

	private final RestDocumentationFilter filter = OAuthSnippet.create();

	@MockitoBean
	private OAuthUseCase oAuthUseCase;

	@DisplayName("사용자가 구글 로그인 API를 통해 토큰을 발급받는다.")
	@Test
	void google_login_success() {
		LoginMemberResponse response = getLoginMemberResponse("access-token", "refresh-token", 3600L);
		doReturn(response).when(oAuthUseCase).login(anyString(), anyString());

		given(this.specification).filter(filter)
			.header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
			.queryParam("code", "authCode")
			.when()
			.get(OAUTH_GOOGLE_URL)
			.then()
			.statusCode(OK.value());
	}

	private static @NotNull LoginMemberResponse getLoginMemberResponse(String expectedAccessToken, String expectedRefreshToken, long expiresIn) {
		return LoginMemberResponse.of(
			UUIDGenerator.generate(),
			expectedAccessToken,
			expectedRefreshToken,
			expiresIn
		);
	}

}
