package com.gomo.app.member.documentation.snippet;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.gomo.app.common.constant.ErrorResponseFields;

public class UpdateProfileImageSnippet {

	private static final String IDENTIFIER = "update_profile_image";
	private static final String SUMMARY = "프로필 이미지 수정 API";
	private static final String DESCRIPTION = "사용자의 프로필 이미지를 수정합니다.";
	private static final String TAG = "Member";
	private static final String MULTIPART_FILE_TYPE = "MultipartFile";

	private static final Snippet REQUEST_FIELDS = requestFields(
		fieldWithPath("profileImage").type(MULTIPART_FILE_TYPE).description("변경하려는 이미지")
	);

	private static final Snippet RESPONSE_FIELDS = responseFields(
		fieldWithPath("profileImageUrl").type(JsonFieldType.STRING).description("변경된 프로필 이미지의 url"),
		fieldWithPath("profileImageName").type(JsonFieldType.STRING).description("변경된 프로필 이미지의 기존 이름")
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.requestSchema(Schema.schema("UpdateProfileImageRequest")),
			REQUEST_FIELDS,
			RESPONSE_FIELDS
		);
	}

	public static RestDocumentationFilter createError() {
		return document(
			IDENTIFIER + "/error",
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.requestSchema(Schema.schema("UpdateProfileImageRequest"))
				.responseSchema(Schema.schema("ErrorResponse")),
			REQUEST_FIELDS,
			ErrorResponseFields.RESPONSE_FIELDS
		);
	}
}
