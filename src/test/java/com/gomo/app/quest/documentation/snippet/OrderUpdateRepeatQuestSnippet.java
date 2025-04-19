package com.gomo.app.quest.documentation.snippet;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.gomo.app.common.constant.ErrorResponseFields;

public class OrderUpdateRepeatQuestSnippet {

	private static final String IDENTIFIER = "order_update_repeat_quest";
	private static final String SUMMARY = "반복 퀘스트 정렬 순서 변경 API";
	private static final String DESCRIPTION = "반복 퀘스트 목록의 정렬 순서를 변경합니다.";
	private static final String TAG = "Quest";

	private static final Snippet REQUEST_FIELDS = requestFields(
		fieldWithPath("questType").type(JsonFieldType.STRING).description("퀘스트 타입: DAILY / WEEKLY / MONTHLY"),
		fieldWithPath("updateOrderRequests").type(JsonFieldType.ARRAY).description("변경 대상 정보"),
		fieldWithPath("updateOrderRequests[].id").type(JsonFieldType.STRING).description("변경 대상 반복 퀘스트 아이디"),
		fieldWithPath("updateOrderRequests[].displayOrder").type(JsonFieldType.NUMBER).description("변경하려는 정렬 순서")
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.requestSchema(Schema.schema("OrderUpdateRepeatQuestRequest")),
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
				.requestSchema(Schema.schema("OrderUpdateRepeatQuestRequest"))
				.responseSchema(Schema.schema("ErrorResponse")),
			REQUEST_FIELDS,
			ErrorResponseFields.RESPONSE_FIELDS
		);
	}
}
