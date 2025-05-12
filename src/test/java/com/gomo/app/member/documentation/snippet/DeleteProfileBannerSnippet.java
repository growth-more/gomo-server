package com.gomo.app.auth.documentation.snippet;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.*;

import org.springframework.restdocs.restassured.RestDocumentationFilter;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.*;

import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.gomo.app.common.constant.ErrorResponseFields;

<<<<<<<< HEAD:src/test/java/com/gomo/app/auth/documentation/snippet/LogoutMemberSnippet.java
public class LogoutMemberSnippet {
	private static final String IDENTIFIER = "logout_member";
	private static final String SUMMARY = "로그아웃 API";
	private static final String DESCRIPTION = "로그아웃을 수행합니다.";
========
public class DeleteProfileBannerSnippet {
	
	private static final String IDENTIFIER = "delete_profile_banner";
	private static final String SUMMARY = "프로필 배너 삭제 API";
	private static final String DESCRIPTION = "사용자의 프로필 배너를 삭제합니다.";
>>>>>>>> 9e18be2 (test: member refactoring 이후, 테스트 코드 작성):src/test/java/com/gomo/app/member/documentation/snippet/DeleteProfileBannerSnippet.java
	private static final String TAG = "Member";

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
		);
	}

	public static RestDocumentationFilter createError() {
		return document(
			IDENTIFIER + "/error",
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.responseSchema(Schema.schema("ErrorResponse")),
			ErrorResponseFields.RESPONSE_FIELDS
		);
	}
}
