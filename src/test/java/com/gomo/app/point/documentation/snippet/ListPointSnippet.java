package com.gomo.app.point.documentation.snippet;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.gomo.app.common.constant.ErrorResponseFields;

public class HistoryPointSnippet {

	private static final String IDENTIFIER = "history_point";
	private static final String SUMMARY = "포인트 이력 조회 API";
	private static final String DESCRIPTION = "사용자의 포인트 획득 및 사용 이력을 조회합니다.";
	private static final String TAG = "Point";

	private static final Snippet RESPONSE_FIELDS = responseFields(
		fieldWithPath("histories").type(JsonFieldType.ARRAY).description("포인트 이력"),
		fieldWithPath("histories[].pointType").type(JsonFieldType.STRING).description("포인트 타입: QUEST / ATTENDANCE / STORE / EVENT"),
		fieldWithPath("histories[].transactionType").type(JsonFieldType.STRING).description("거래 타입: GAIN / SPEND"),
		fieldWithPath("histories[].points").type(JsonFieldType.NUMBER).description("포인트 양"),
		fieldWithPath("histories[].description").type(JsonFieldType.STRING).description("포인트에 대한 설명"),
		fieldWithPath("histories[].transactionDateTime").type(JsonFieldType.STRING).description("거래 날짜")
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.responseSchema(Schema.schema("HistoryListPointResponse")),
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
