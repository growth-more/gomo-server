package com.gomo.app.member.documentation;

import com.gomo.app.common.DocumentationTestBase;
import com.gomo.app.member.common.util.MemberDBDataHelper;
import com.gomo.app.member.documentation.snippet.DeleteMemberBannerSnippet;
import com.gomo.app.member.documentation.snippet.DeleteMemberSnippet;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import static io.restassured.RestAssured.given;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@DisplayName("[Presentation documentation]: 회원 프로필배너 삭제 테스트")
public class DeleteProfileBannerDocumentationTest extends DocumentationTestBase {

    private static final String MEMBER_BANNER_DELETE_URL = "/members/images/banners";

    private final RestDocumentationFilter filter = DeleteMemberBannerSnippet.create();
    private final RestDocumentationFilter errorFilter = DeleteMemberBannerSnippet.createError();

    @Autowired
	private MemberDBDataHelper memberDBDataHelper;

	@AfterEach
	void tearDown() {
		memberDBDataHelper.cleanUp();
	}

    @DisplayName("사용자가 배너 삭제를 요청한다.")
    @Test
    void delete_member(){
        given(this.specification).filter(filter)
                .header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
                .header(AUTHORIZATION, "Bearer " + accessToken)
                .when()
                .delete(MEMBER_BANNER_DELETE_URL)
                .then()
                .statusCode(OK.value());
    }
}
