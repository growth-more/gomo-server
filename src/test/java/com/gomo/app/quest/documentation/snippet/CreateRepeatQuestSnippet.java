package com.gomo.app.quest.documentation.snippet;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.gomo.app.common.constant.ErrorResponseFields;

public class CreateRepeatQuestSnippet {

	private static final String IDENTIFIER = "create_repeat_quest";
	private static final String SUMMARY = "반복 퀘스트 생성 API";
	private static final String DESCRIPTION = "사용자가 반복 퀘스트를 생성합니다.";
	private static final String TAG = "Quest";

	private static final Snippet REQUEST_FIELDS = requestFields(
		fieldWithPath("subjectId").type(JsonFieldType.STRING).description("퀘스트 주제(관심사) 아이디"),
		fieldWithPath("questType").type(JsonFieldType.STRING).description("퀘스트 타입: DAILY / WEEKLY / MONTHLY"),
		fieldWithPath("content").type(JsonFieldType.STRING).description("퀘스트 내용")
	);

	private static final Snippet RESPONSE_FIELDS = responseFields(
		fieldWithPath("id").type(JsonFieldType.STRING).description("반복 퀘스트 아이디")
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.requestSchema(Schema.schema("CreateRepeatQuestRequest"))
				.responseSchema(Schema.schema("CreateRepeatQuestResponse")),
			REQUEST_FIELDS,
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
				.requestSchema(Schema.schema("CreateRepeatQuestRequest"))
				.responseSchema(Schema.schema("ErrorResponse")),
			REQUEST_FIELDS,
			ErrorResponseFields.RESPONSE_FIELDS
		);
	}
}
