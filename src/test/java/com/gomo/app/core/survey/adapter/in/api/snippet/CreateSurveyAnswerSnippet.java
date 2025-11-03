package com.gomo.app.core.survey.adapter.in.api.snippet;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import com.gomo.app.test.ErrorResponseFields;

public class CreateSurveyAnswerSnippet {

	private static final String IDENTIFIER = "survey-answer-create";

	private static final Snippet REQUEST_HEADERS = requestHeaders(
		headerWithName(CONTENT_TYPE).description("Content-Type: `application/json`"),
		headerWithName(AUTHORIZATION).description("JWT Access Token (Bearer)")
	);

	private static final Snippet REQUEST_FIELDS = requestFields(
		fieldWithPath("surveyResult").type(JsonFieldType.ARRAY).description("설문 결과 목록"),
		fieldWithPath("surveyResult[].surveyQuestionId").type(JsonFieldType.STRING).description("설문 문항 ID"),
		fieldWithPath("surveyResult[].surveyItemId").type(JsonFieldType.STRING).description("선택한 설문 항목 ID"),
		fieldWithPath("surveyResult[].surveyItemContent").type(JsonFieldType.STRING).description("선택한 설문 항목의 내용"),
		fieldWithPath("surveyResult[].customAnswer").type(JsonFieldType.STRING).optional().description("기타 항목 선택 시, 사용자가 직접 입력한 내용 (없을 경우 null)")
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
