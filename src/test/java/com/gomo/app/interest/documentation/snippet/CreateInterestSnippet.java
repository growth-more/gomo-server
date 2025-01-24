package com.gomo.app.interest.documentation.snippet;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.gomo.app.common.constant.ErrorResponseFields;

public class CreateInterestSnippet {

	private static final String IDENTIFIER = "create_interest";
	private static final String SUMMARY = "관심사 등록 API";
	private static final String DESCRIPTION = "사용자의 관심사를 등록합니다.";
	private static final String TAG = "Interest";
	private static final String MULTIPART_FILE_TYPE = "MultipartFile";

	private static final Snippet REQUEST_FIELDS = requestFields(
		fieldWithPath("name").type(JsonFieldType.STRING).description("관심사 이름"),
		fieldWithPath("logo").type(MULTIPART_FILE_TYPE).description("로고 이미지")
	);

	private static final Snippet RESPONSE_FIELDS = responseFields(
		fieldWithPath("id").type(JsonFieldType.STRING).description("관심사 아이디: 임의의 고유 식별 문자")
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.requestSchema(Schema.schema("CreateInterestRequest"))
				.responseSchema(Schema.schema("CreateInterestResponse")),
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
				.requestSchema(Schema.schema("CreateInterestRequest"))
				.responseSchema(Schema.schema("ErrorResponse")),
			REQUEST_FIELDS,
			ErrorResponseFields.RESPONSE_FIELDS
		);
	}
}
