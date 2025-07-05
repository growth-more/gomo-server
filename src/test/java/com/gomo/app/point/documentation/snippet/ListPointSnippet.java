package com.gomo.app.point.documentation.snippet;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import com.gomo.app.common.constant.ErrorResponseFields;

public class ListPointSnippet {

	private static final String IDENTIFIER = "point-list";

	private static final Snippet REQUEST_HEADERS = requestHeaders(
		headerWithName(AUTHORIZATION).description("JWT Access Token (Bearer)")
	);

	private static final Snippet QUERY_PARAMETERS = queryParameters(
		parameterWithName("size").description("한 번에 조회할 포인트 내역 개수 (기본값: 10)"),
		parameterWithName("lastElementId").description("이전 페이지의 마지막 요소 ID. 이 파라미터가 없으면 첫 페이지를 조회합니다.").optional()
	);

	private static final Snippet RESPONSE_FIELDS = responseFields(
		fieldWithPath("points").type(JsonFieldType.ARRAY).description("포인트 거래 내역 목록"),
		fieldWithPath("lastElementId").type(JsonFieldType.STRING).description("현재 페이지의 마지막 요소 ID. 다음 페이지 조회 시 이 값을 사용합니다.").optional(),
		fieldWithPath("points[].id").type(JsonFieldType.STRING).description("포인트 거래 내역의 고유 ID"),
		fieldWithPath("points[].sourceType").type(JsonFieldType.STRING).description("포인트 발생 출처: `QUEST`, `ATTENDANCE`, `STORE`, `EVENT` 등"),
		fieldWithPath("points[].transactionType").type(JsonFieldType.STRING).description("거래 유형: `GAIN`(획득), `SPEND`(사용)"),
		fieldWithPath("points[].amount").type(JsonFieldType.NUMBER).description("거래된 포인트 양"),
		fieldWithPath("points[].description").type(JsonFieldType.STRING).description("포인트 거래에 대한 설명 (예: '일일 퀘스트 완료 보상')"),
		fieldWithPath("points[].transactionDateTime").type(JsonFieldType.STRING).description("거래 발생 일시 (ISO 8601 형식)")
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			preprocessRequest(prettyPrint()),
			preprocessResponse(prettyPrint()),
			REQUEST_HEADERS,
			QUERY_PARAMETERS,
			RESPONSE_FIELDS
		);
	}

	public static RestDocumentationFilter createError() {
		return document(
			IDENTIFIER + "-error",
			preprocessRequest(prettyPrint()),
			preprocessResponse(prettyPrint()),
			REQUEST_HEADERS,
			QUERY_PARAMETERS,
			ErrorResponseFields.RESPONSE_FIELDS
		);
	}
}
