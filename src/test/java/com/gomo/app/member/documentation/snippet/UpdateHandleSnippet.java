package com.gomo.app.member.documentation.snippet;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.gomo.app.common.constant.ErrorResponseFields;

public class UpdateHandleSnippet {

	private static final String IDENTIFIER = "update_handle";
	private static final String SUMMARY = "핸들 변경 API";
	private static final String DESCRIPTION = "사용자 핸들을 변경합니다.";
	private static final String TAG = "Member";

	private static final Snippet REQUEST_FIELDS = requestFields(
		fieldWithPath("handle").type(JsonFieldType.STRING).description("사용자가 지정한 고유 식별 문자")
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.requestSchema(Schema.schema("UpdateHandleRequest")),
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
				.requestSchema(Schema.schema("UpdateHandleRequest"))
				.responseSchema(Schema.schema("ErrorResponse")),
			REQUEST_FIELDS,
			ErrorResponseFields.RESPONSE_FIELDS
		);
	}
}
