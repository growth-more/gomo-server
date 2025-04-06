package com.gomo.app.member.documentation.snippet;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.gomo.app.common.constant.ErrorResponseFields;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;

public class UpdateProfileBannerSnippet {

	private static final String IDENTIFIER = "update_profile_banner";
	private static final String SUMMARY = "프로필 배너 수정 API";
	private static final String DESCRIPTION = "사용자의 프로필 배너를 수정합니다.";
	private static final String TAG = "Member";

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.requestSchema(Schema.schema("UpdateProfileBannerRequest"))
		);
	}

	public static RestDocumentationFilter createError() {
		return document(
			IDENTIFIER + "/error",
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.requestSchema(Schema.schema("UpdateProfileBannerRequest"))
				.responseSchema(Schema.schema("ErrorResponse")),
			ErrorResponseFields.RESPONSE_FIELDS
		);
	}
}
