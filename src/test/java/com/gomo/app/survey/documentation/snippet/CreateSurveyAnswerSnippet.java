package com.gomo.app.survey.documentation.snippet;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.gomo.app.common.constant.ErrorResponseFields;

public class CreateSurveyAnswerSnippet {

	private static final String IDENTIFIER = "create_survey_answer";
	private static final String SUMMARY = "설문 답변 등록 API";
	private static final String DESCRIPTION = "사용자의 설문 답변을 등록합니다.";
	private static final String TAG = "Survey";

	private static final Snippet REQUEST_FIELDS = requestFields(
		fieldWithPath("surveyResult").type(JsonFieldType.ARRAY).description("설문 결과 목록"),
		fieldWithPath("surveyResult[].surveyQuestionId").type(JsonFieldType.STRING).description("설문 문항"),
		fieldWithPath("surveyResult[].surveyItemId").type(JsonFieldType.STRING).description("선택한 항목"),
		fieldWithPath("surveyResult[].surveyItemContent").type(JsonFieldType.STRING).description("설문 항목 내용"),
		fieldWithPath("surveyResult[].customAnswer").type(JsonFieldType.STRING).optional().description("사용자가 입력한 내용")
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.requestSchema(Schema.schema("CreateSurveyResultRequest")),
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
				.requestSchema(Schema.schema("CreateSurveyResultRequest"))
				.responseSchema(Schema.schema("ErrorResponse")),
			REQUEST_FIELDS,
			ErrorResponseFields.RESPONSE_FIELDS
		);
	}
}
