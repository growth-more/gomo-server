package com.gomo.app.point.documentation.snippet;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.gomo.app.common.constant.ErrorResponseFields;

public class ListPointSnippet {

	private static final String IDENTIFIER = "list_point";
	private static final String SUMMARY = "포인트 목록 조회 API";
	private static final String DESCRIPTION = "사용자의 포인트 획득 및 사용 목록을 조회합니다.";
	private static final String TAG = "Point";

	private static final ParameterDescriptor[] LIST_POINT_QUERY_PARAMETERS = {
		parameterWithName("size").description("페이지 크기"),
		parameterWithName("lastElementId").description("이전 페이지의 마지막 포인트 아이디").optional()
	};

	private static final Snippet RESPONSE_FIELDS = responseFields(
		fieldWithPath("points").type(JsonFieldType.ARRAY).description("포인트 목록"),
		fieldWithPath("lastElementId").type(JsonFieldType.STRING).description("현재 페이지의 마지막 포인트 아이디"),
		fieldWithPath("points[].id").type(JsonFieldType.STRING).description("포인트 아이디"),
		fieldWithPath("points[].sourceType").type(JsonFieldType.STRING).description("발원지: QUEST / ATTENDANCE / STORE / EVENT"),
		fieldWithPath("points[].transactionType").type(JsonFieldType.STRING).description("거래 타입: GAIN / SPEND"),
		fieldWithPath("points[].amount").type(JsonFieldType.NUMBER).description("포인트 양"),
		fieldWithPath("points[].description").type(JsonFieldType.STRING).description("포인트에 대한 설명"),
		fieldWithPath("points[].transactionDateTime").type(JsonFieldType.STRING).description("거래 날짜")
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.queryParameters(LIST_POINT_QUERY_PARAMETERS)
				.responseSchema(Schema.schema("ListPointResponse")),
			RESPONSE_FIELDS,
			queryParameters(LIST_POINT_QUERY_PARAMETERS)
		);
	}

	public static RestDocumentationFilter createError() {
		return document(
			IDENTIFIER + "/error",
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.queryParameters(LIST_POINT_QUERY_PARAMETERS)
				.responseSchema(Schema.schema("ErrorResponse")),
			ErrorResponseFields.RESPONSE_FIELDS,
			queryParameters(LIST_POINT_QUERY_PARAMETERS)
		);
	}
}
