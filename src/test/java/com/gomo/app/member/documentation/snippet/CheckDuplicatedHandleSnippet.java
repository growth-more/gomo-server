package com.gomo.app.member.documentation.snippet;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.*;

import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.gomo.app.common.constant.ErrorResponseFields;

public class CheckDuplicatedHandleSnippet {

	private static final String IDENTIFIER = "check_duplicated_handle";
	private static final String SUMMARY = "핸들 중복 확인 API";
	private static final String DESCRIPTION = "핸들 중복 및 유효성을 체크하고 상태 코드를 반환합니다.";
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
				.tag(TAG),
			ErrorResponseFields.RESPONSE_FIELDS
		);
	}
}
