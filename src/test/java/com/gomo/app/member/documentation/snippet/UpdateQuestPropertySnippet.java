package com.gomo.app.member.documentation.snippet;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.gomo.app.common.constant.ErrorResponseFields;

public class UpdateQuestPropertySnippet {

	private static final String IDENTIFIER = "update_quest_property";
	private static final String SUMMARY = "퀘스트 설정값 수정 API";
	private static final String DESCRIPTION = "사용자의 퀘스트 설정값을 수정합니다.";
	private static final String TAG = "Member";

	private static final Snippet REQUEST_FIELDS = requestFields(
		fieldWithPath("dailyThreshold").type(JsonFieldType.NUMBER).description("일일 퀘스트 생성 제한 수치"),
		fieldWithPath("weeklyThreshold").type(JsonFieldType.NUMBER).description("주간 퀘스트 생성 제한 수치"),
		fieldWithPath("monthlyThreshold").type(JsonFieldType.NUMBER).description("월간 퀘스트 생성 제한 수치")
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.requestSchema(Schema.schema("UpdateQuestPropertyRequest")),
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
				.requestSchema(Schema.schema("UpdateQuestPropertyRequest"))
				.responseSchema(Schema.schema("ErrorResponse")),
			REQUEST_FIELDS,
			ErrorResponseFields.RESPONSE_FIELDS
		);
	}
}
