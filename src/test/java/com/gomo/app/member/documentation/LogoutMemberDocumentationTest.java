package com.gomo.app.member.documentation;

import static io.restassured.RestAssured.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.MediaType.*;

import com.gomo.app.common.util.LoginMemberHelper;
import com.gomo.app.member.documentation.snippet.LogoutMemberSnippet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.gomo.app.common.DocumentationTestBase;

@DisplayName("[Presentation documentation]: 회원 로그아웃 테스트")
public class LogoutMemberDocumentationTest extends DocumentationTestBase {

    private static final String LOGOUT_URL = "/members/logout";

    private final RestDocumentationFilter filter = LogoutMemberSnippet.create();

    private static final String EMAIL = "gomotest@naver.com";
	private static final String PASSWORD = "Gomotest1234@";

	private String token;

	@Autowired
    LoginMemberHelper loginMemberHelper;

	@BeforeEach
	public void setUp() {
		token = loginMemberHelper.getAccessToken(EMAIL, PASSWORD);
	}

//    @DisplayName("사용자가 로그아웃한다.")
//    @Test
//    void logout_member(){
//        given(this.specification).filter(filter)
//                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
//                .header(AUTHORIZATION, "Bearer " + token)
//                .when()
//                .get(LOGOUT_URL)
//                .then()
//                .statusCode(OK.value());
//    }
}
