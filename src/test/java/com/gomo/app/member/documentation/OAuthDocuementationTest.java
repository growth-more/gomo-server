package com.gomo.app.member.documentation;

import static io.restassured.RestAssured.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import com.gomo.app.common.util.UUIDGenerator;
import com.gomo.app.member.application.OAuthUseCase;
import com.gomo.app.member.documentation.snippet.OAuthSnippet;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.presentation.response.LoginMemberResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@DisplayName("[Presentation documentation]: OAuth 로그인 테스트")
public class OAuthDocuementationTest extends DocumentationTestBase {

    private static final String OAUTH_GOOGLE_URL="/oauth/login/google";

    private final RestDocumentationFilter filter = OAuthSnippet.create();

    @MockitoBean
    private OAuthUseCase oAuthUseCase;

    @DisplayName("사용자가 구글 로그인 API를 통해 토큰을 발급받는다.")
    @Test
    void google_login_success(){

        // given
        MemberId expectedId = MemberId.of(UUIDGenerator.generate());
        String expectedAccessToken = "access-token";
        String expectedRefreshToken = "refresh-token";
        long expiresIn = 3600L; // 초 단위 만료시간

        LoginMemberResponse expected = LoginMemberResponse.of(
                expectedId,
                expectedAccessToken,
                expectedRefreshToken,
                expiresIn
        );
        doReturn(expected).when(oAuthUseCase).login(anyString());

        given(this.specification).filter(filter)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .queryParam("code", "authCode")
                .when()
                .get(OAUTH_GOOGLE_URL)
                .then()
                .statusCode(OK.value());
    }

}
