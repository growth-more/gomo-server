package com.gomo.app.core.interest.presentation.documentation.snippet;

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

public class ReadInterestSnippet {

	private static final String IDENTIFIER = "interest-find-by-id";

	private static final Snippet REQUEST_HEADERS = requestHeaders(
		headerWithName(AUTHORIZATION).description("JWT Access Token (Bearer)")
	);

	private static final Snippet PATH_PARAMETERS = pathParameters(
		parameterWithName("id").description("조회할 관심사의 ID (UUID)")
	);

	private static final Snippet RESPONSE_FIELDS = responseFields(
		fieldWithPath("id").type(JsonFieldType.STRING).description("관심사 아이디"),
		fieldWithPath("registrantId").type(JsonFieldType.STRING).description("등록자 아이디"),
		fieldWithPath("name").type(JsonFieldType.STRING).description("관심사 이름"),
		fieldWithPath("logoUrl").type(JsonFieldType.STRING).description("로고 이미지 URL"),
		fieldWithPath("colorCode").type(JsonFieldType.STRING).description("관심사 색상 코드"),
		fieldWithPath("level").type(JsonFieldType.NUMBER).description("레벨"),
		fieldWithPath("score").type(JsonFieldType.NUMBER).description("현재 레벨의 경험치"),
		fieldWithPath("scoreThreshold").type(JsonFieldType.NUMBER).description("다음 레벨업에 필요한 경험치"),
		fieldWithPath("totalScore").type(JsonFieldType.NUMBER).description("누적 경험치"),
		fieldWithPath("majorInterestId").type(JsonFieldType.STRING).description("주요 관심사 아이디").optional()
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			preprocessRequest(prettyPrint()),
			preprocessResponse(prettyPrint()),
			REQUEST_HEADERS,
			PATH_PARAMETERS,
			RESPONSE_FIELDS
		);
	}

	public static RestDocumentationFilter createError() {
		return document(
			IDENTIFIER + "-error",
			preprocessRequest(prettyPrint()),
			preprocessResponse(prettyPrint()),
			REQUEST_HEADERS,
			PATH_PARAMETERS,
			ErrorResponseFields.RESPONSE_FIELDS
		);
	}
}
