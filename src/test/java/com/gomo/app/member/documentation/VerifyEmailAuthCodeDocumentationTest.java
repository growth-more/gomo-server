package com.gomo.app.member.documentation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.doReturn;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import com.gomo.app.member.documentation.snippet.VerifyEmailAuthCodeSnippet;
import com.gomo.app.member.infrastructure.EmailAuthRedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@DisplayName("[Presentation documentation]: 이메일 인증 코드 검증 테스트")
public class VerifyEmailAuthCodeDocumentationTest extends DocumentationTestBase {
    private static final String EMAIL_VERIFY_URL = "/members/emails/codes/auth";

    private final RestDocumentationFilter filter = VerifyEmailAuthCodeSnippet.create();
    private final RestDocumentationFilter errorfilter = VerifyEmailAuthCodeSnippet.createError();

    @MockitoBean
    private EmailAuthRedisService emailAuthRedisService;

    @BeforeEach
    void setup(){
        doReturn("111111").when(emailAuthRedisService).getAuthCode("test@gmail.com");
    }

    @DisplayName("사용자가 이메일 인증 코드 검증을 요청한다.")
    @Test
    void verify_auth_code() {
        given(this.specification).filter(filter)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .param("email", "test@gmail.com")
                .param("code", "111111")
                .when()
                .get(EMAIL_VERIFY_URL)
                .then()
                .statusCode(OK.value())
                .body("email", instanceOf(String.class));
    }
}
