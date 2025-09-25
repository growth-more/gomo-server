package com.gomo.app.core.survey.documentation.snippet;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import com.gomo.app.common.constant.ErrorResponseFields;

public class ListSurveySnippet {

	private static final String IDENTIFIER = "survey-list-find";

	private static final Snippet REQUEST_HEADERS = requestHeaders(
		headerWithName(AUTHORIZATION).description("JWT Access Token (Bearer)")
	);

	private static final Snippet RESPONSE_FIELDS = responseFields(
		fieldWithPath("surveyQuestions").type(JsonFieldType.ARRAY).description("설문 문항 목록"),
		fieldWithPath("surveyQuestions[].id").type(JsonFieldType.STRING).description("설문 문항의 고유 ID"),
		fieldWithPath("surveyQuestions[].questionSelectType").type(JsonFieldType.STRING).description("선택 타입: `SINGLE` (단일 선택), `MULTIPLE` (다중 선택)"),
		fieldWithPath("surveyQuestions[].required").type(JsonFieldType.BOOLEAN).description("필수 응답 여부"),
		fieldWithPath("surveyQuestions[].content").type(JsonFieldType.STRING).description("설문 문항 내용"),
		fieldWithPath("surveyQuestions[].surveyItems").type(JsonFieldType.ARRAY).description("설문 문항에 대한 선택지 목록"),
		fieldWithPath("surveyQuestions[].surveyItems[].id").type(JsonFieldType.STRING).description("선택지의 고유 ID"),
		fieldWithPath("surveyQuestions[].surveyItems[].content").type(JsonFieldType.STRING).description("선택지 내용"),
		fieldWithPath("surveyQuestions[].surveyItems[].displayOrder").type(JsonFieldType.NUMBER).description("선택지 정렬 순서")
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
