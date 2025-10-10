package com.gomo.app.core.interest.presentation.documentation.snippet;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import com.gomo.app.test.ErrorResponseFields;

public class ListMajorInterestSnippet {

	private static final String IDENTIFIER = "list_major_interest";

	private static final Snippet REQUEST_HEADERS = requestHeaders(
		headerWithName(AUTHORIZATION).description("JWT Access Token (Bearer)")
	);

	private static final Snippet RESPONSE_FIELDS = responseFields(
		fieldWithPath("majorInterests").type(JsonFieldType.ARRAY).description("주요 관심사 목록"),
		fieldWithPath("majorInterests[].id").type(JsonFieldType.STRING).description("주요 관심사 아이디"),
		fieldWithPath("majorInterests[].interestId").type(JsonFieldType.STRING).description("관심사 아이디"),
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
			preprocessRequest(prettyPrint()),
			preprocessResponse(prettyPrint()),
			REQUEST_HEADERS,
			RESPONSE_FIELDS
		);
	}

	public static RestDocumentationFilter createError() {
		return document(
			IDENTIFIER + "-error",
			preprocessRequest(prettyPrint()),
			preprocessResponse(prettyPrint()),
			REQUEST_HEADERS,
			ErrorResponseFields.RESPONSE_FIELDS
		);
	}
}
