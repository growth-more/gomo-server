package com.gomo.app.quest.documentation.snippet;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import com.gomo.app.common.constant.ErrorResponseFields;

public class OrderUpdateAssignQuestSnippet {

	private static final String IDENTIFIER = "quest-assign-order-update";

	private static final Snippet REQUEST_HEADERS = requestHeaders(
		headerWithName(CONTENT_TYPE).description("Content-Type: `application/json`"),
		headerWithName(AUTHORIZATION).description("JWT Access Token (Bearer)")
	);

	private static final Snippet REQUEST_FIELDS = requestFields(
		fieldWithPath("questType").type(JsonFieldType.STRING).description("정렬 순서를 변경할 퀘스트 타입: `DAILY`, `WEEKLY`, `MONTHLY`"),
		fieldWithPath("updatedOrders").type(JsonFieldType.ARRAY).description("정렬 순서 변경 요청 목록"),
		fieldWithPath("updatedOrders[].orderChangeableId").type(JsonFieldType.STRING).description("변경 대상 할당 퀘스트 아이디"),
		fieldWithPath("updatedOrders[].displayOrder").type(JsonFieldType.NUMBER).description("새로운 정렬 순서 (0부터 시작)")
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			preprocessRequest(prettyPrint()),
			preprocessResponse(prettyPrint()),
			REQUEST_HEADERS,
			REQUEST_FIELDS
		);
	}

	public static RestDocumentationFilter createError() {
		return document(
			IDENTIFIER + "-error",
			preprocessRequest(prettyPrint()),
			preprocessResponse(prettyPrint()),
			REQUEST_HEADERS,
			REQUEST_FIELDS,
			ErrorResponseFields.RESPONSE_FIELDS
		);
	}
}
