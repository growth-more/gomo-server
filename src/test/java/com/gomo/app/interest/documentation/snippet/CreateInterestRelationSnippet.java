package com.gomo.app.interest.documentation.snippet;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.gomo.app.common.constant.ErrorResponseFields;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

public class CreateInterestRelationSnippet {

	private static final String IDENTIFIER = "create_interest_relation";
	private static final String SUMMARY = "관심사 계층 구조 추가 API";
	private static final String DESCRIPTION = "두 가지 관심사 사이의 연결선을 추가합니다.";
	private static final String TAG = "Interest";

	private static final Snippet REQUEST_FIELDS = requestFields(
		fieldWithPath("parentInterestId").type(JsonFieldType.STRING).description("상위 관심사 아이디"),
		fieldWithPath("childInterestId").type(JsonFieldType.STRING).description("하위 관심사 아이디")
	);

	private static final Snippet RESPONSE_FIELDS = responseFields(
		fieldWithPath("id").type(JsonFieldType.STRING).description("관심사 간선 아이디")
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.requestSchema(Schema.schema("CreateInterestRelationRequest"))
				.responseSchema(Schema.schema("CreateInterestRelationResponse")),
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
				.requestSchema(Schema.schema("CreateInterestRelationRequest"))
				.responseSchema(Schema.schema("ErrorResponse")),
			REQUEST_FIELDS,
			ErrorResponseFields.RESPONSE_FIELDS
		);
	}
}
