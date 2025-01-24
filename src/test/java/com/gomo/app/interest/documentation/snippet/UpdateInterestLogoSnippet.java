package com.gomo.app.interest.documentation.snippet;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.gomo.app.common.constant.ErrorResponseFields;

public class UpdateInterestLogoSnippet {

	private static final String IDENTIFIER = "update_interest_logo";
	private static final String SUMMARY = "관심사 로고 이미지 수정 API";
	private static final String DESCRIPTION = "사용자 관심사의 로고 이미지를 수정합니다.";
	private static final String TAG = "Interest";
	private static final String MULTIPART_FILE_TYPE = "MultipartFile";

	private static final Snippet REQUEST_FIELDS = requestFields(
		fieldWithPath("updatedLogo").type(MULTIPART_FILE_TYPE).description("로고 이미지")
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.requestSchema(Schema.schema("LogoUpdateInterestRequest")),
			REQUEST_FIELDS
		);
	}

	public static RestDocumentationFilter createError() {
		return document(
			IDENTIFIER + "/error",
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.requestSchema(Schema.schema("LogoUpdateInterestRequest"))
				.responseSchema(Schema.schema("ErrorResponse")),
			REQUEST_FIELDS,
			ErrorResponseFields.RESPONSE_FIELDS
		);
	}
}
