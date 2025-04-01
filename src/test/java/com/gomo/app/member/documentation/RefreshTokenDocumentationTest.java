package com.gomo.app.member.documentation;

import static io.restassured.RestAssured.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import com.gomo.app.member.documentation.snippet.RefreshTokenSnippet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;


@DisplayName("[Presentation documentation]: RefreshToken 재발급 테스트")
public class RefreshTokenDocumentationTest extends DocumentationTestBase {

    private static final String REFRESH_TOKEN_URL = "/members/refresh";

    private final RestDocumentationFilter filter = RefreshTokenSnippet.create();
    private final RestDocumentationFilter errorFilter = RefreshTokenSnippet.createError();

    @DisplayName("Access Token 갱신에 성공한다.")
    @Test
    void refresh_access_token(){
        given(this.specification).filter(filter)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .cookie("refreshToken", refreshToken)
                .when()
                .post(REFRESH_TOKEN_URL)
                .then()
                .statusCode(OK.value());
    }
}
