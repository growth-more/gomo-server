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

public class ListInterestSnippet {

	private static final String IDENTIFIER = "list_interest";
	private static final String SUMMARY = "관심사 목록 조회 API";
	private static final String DESCRIPTION = "사용자의 관심사 목록을 조회합니다.";
	private static final String TAG = "Interest";

	private static final Snippet RESPONSE_FIELDS = responseFields(
		fieldWithPath("interests").type(JsonFieldType.ARRAY).description("관심사 목록"),
		fieldWithPath("interests[].id").type(JsonFieldType.STRING).description("관심사 아이디"),
		fieldWithPath("interests[].registrantId").type(JsonFieldType.STRING).description("사용자 아이디"),
		fieldWithPath("interests[].name").type(JsonFieldType.STRING).description("관심사 이름"),
		fieldWithPath("interests[].logoUrl").type(JsonFieldType.STRING).description("관심사 로고 이미지"),
		fieldWithPath("interests[].level").type(JsonFieldType.NUMBER).description("레벨"),
		fieldWithPath("interests[].score").type(JsonFieldType.NUMBER).description("현재 점수"),
		fieldWithPath("interests[].scoreThreshold").type(JsonFieldType.NUMBER).description("현재 레벨의 임계 점수"),
		fieldWithPath("interests[].totalScore").type(JsonFieldType.NUMBER).description("전체 점수")
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.responseSchema(Schema.schema("ListInterestResponse")),
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
