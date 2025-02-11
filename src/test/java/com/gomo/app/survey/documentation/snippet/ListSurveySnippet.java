package com.gomo.app.survey.documentation.snippet;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.gomo.app.common.constant.ErrorResponseFields;

public class ListSurveySnippet {

	private static final String IDENTIFIER = "list_survey";
	private static final String SUMMARY = "설문 목록 조회 API";
	private static final String DESCRIPTION = "사용자 정보를 수집하는 설문 목록을 조회합니다.";
	private static final String TAG = "Survey";

	private static final Snippet RESPONSE_FIELDS = responseFields(
		fieldWithPath("surveyQuestions").type(JsonFieldType.ARRAY).description("문항 목록"),
		fieldWithPath("surveyQuestions[].id").type(JsonFieldType.STRING).description("문항 아이디"),
		fieldWithPath("surveyQuestions[].questionSelectType").type(JsonFieldType.STRING).description("선택 타입: SINGLE / MULTIPLE"),
		fieldWithPath("surveyQuestions[].required").type(JsonFieldType.BOOLEAN).description("필수 여부"),
		fieldWithPath("surveyQuestions[].content").type(JsonFieldType.STRING).description("문항 내용"),
		fieldWithPath("surveyQuestions[].surveyItems").type(JsonFieldType.ARRAY).description("선택지 목록"),
		fieldWithPath("surveyQuestions[].surveyItems[].id").type(JsonFieldType.STRING).description("선택지 아이디"),
		fieldWithPath("surveyQuestions[].surveyItems[].content").type(JsonFieldType.STRING).description("선택지 내용"),
		fieldWithPath("surveyQuestions[].surveyItems[].displayOrder").type(JsonFieldType.NUMBER).description("선택지 정렬 순서")
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.responseSchema(Schema.schema("ListSurveyQuestionResponse")),
			RESPONSE_FIELDS
		);
	}

	public static RestDocumentationFilter createError() {
		return document(
			IDENTIFIER + "/error",
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.responseSchema(Schema.schema("ErrorResponse")),
			ErrorResponseFields.RESPONSE_FIELDS
		);
	}
}
