package com.gomo.app.core.streak.presentation.documentation.snippet;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import com.gomo.app.common.constant.ErrorResponseFields;

public class ReadAchieverSnippet {

	private static final String IDENTIFIER = "achiever-read";

	private static final Snippet REQUEST_HEADERS = requestHeaders(
		headerWithName(AUTHORIZATION).description("JWT Access Token (Bearer)")
	);

	private static final Snippet RESPONSE_FIELDS = responseFields(
		fieldWithPath("id").type(JsonFieldType.STRING).description("성취자 정보의 고유 ID"),
		fieldWithPath("currentStreakDays").type(JsonFieldType.NUMBER).description("현재 연속 스트릭 유지일 수"),
		fieldWithPath("longestStreakDays").type(JsonFieldType.NUMBER).description("역대 최장 스트릭 유지일 수")
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
