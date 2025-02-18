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

public class ListMajorInterestSnippet {

	private static final String IDENTIFIER = "list_major_interest";
	private static final String SUMMARY = "주요 관심사 목록 조회 API";
	private static final String DESCRIPTION = "사용자의 주요 관심사 목록을 조회합니다.";
	private static final String TAG = "Interest";

	private static final Snippet RESPONSE_FIELDS = responseFields(
		fieldWithPath("majorInterests").type(JsonFieldType.ARRAY).description("주요 관심사 목록"),
		fieldWithPath("majorInterests[].id").type(JsonFieldType.STRING).description("주요 관심사 아이디"),
		fieldWithPath("majorInterests[].name").type(JsonFieldType.STRING).description("관심사 이름"),
		fieldWithPath("majorInterests[].logoUrl").type(JsonFieldType.STRING).description("관심사 로고 이미지"),
		fieldWithPath("majorInterests[].level").type(JsonFieldType.NUMBER).description("레벨"),
		fieldWithPath("majorInterests[].score").type(JsonFieldType.NUMBER).description("현재 점수"),
		fieldWithPath("majorInterests[].scoreThreshold").type(JsonFieldType.NUMBER).description("현재 레벨의 임계 점수"),
		fieldWithPath("majorInterests[].displayOrder").type(JsonFieldType.NUMBER).description("정렬 순서")
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.responseSchema(Schema.schema("ListMajorInterestResponse")),
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
