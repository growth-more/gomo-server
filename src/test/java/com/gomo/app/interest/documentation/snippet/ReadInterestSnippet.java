package com.gomo.app.interest.documentation.snippet;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.gomo.app.common.constant.ErrorResponseFields;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;

public class ReadInterestSnippet {

	private static final String IDENTIFIER = "read_interest";
	private static final String SUMMARY = "관심사 조회 API";
	private static final String DESCRIPTION = "사용자의 관심사를 조회합니다.";
	private static final String TAG = "Interest";

	private static final Snippet RESPONSE_FIELDS = responseFields(
		fieldWithPath("id").type(JsonFieldType.STRING).description("관심사 아이디"),
		fieldWithPath("registrantId").type(JsonFieldType.STRING).description("등록자 아이디"),
		fieldWithPath("name").type(JsonFieldType.STRING).description("관심사 이름"),
		fieldWithPath("logoUrl").type(JsonFieldType.STRING).description("로고 이미지"),
		fieldWithPath("level").type(JsonFieldType.NUMBER).description("레벨"),
		fieldWithPath("score").type(JsonFieldType.NUMBER).description("현재 점수"),
		fieldWithPath("scoreThreshold").type(JsonFieldType.NUMBER).description("현재 레벨의 임계 점수"),
		fieldWithPath("totalScore").type(JsonFieldType.NUMBER).description("전체 점수")
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.responseSchema(Schema.schema("ReadInterestResponse")),
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
				.responseSchema(Schema.schema("ErrorResponse")),
			ErrorResponseFields.RESPONSE_FIELDS
		);
	}
}
