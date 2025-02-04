package com.gomo.app.quest.documentation.snippet;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.gomo.app.common.constant.ErrorResponseFields;

public class ListRepeatQuestSnippet {

	private static final String IDENTIFIER = "list_repeat_quest";
	private static final String SUMMARY = "반복 퀘스트 목록 조회 API";
	private static final String DESCRIPTION = "사용자가 반복 퀘스트 목록을 조회합니다.";
	private static final String TAG = "Quest";	

	private static final Snippet RESPONSE_FIELDS = responseFields(
		fieldWithPath("dailyQuests").type(JsonFieldType.ARRAY).description("일간 퀘스트 목록"),
		fieldWithPath("dailyQuests[].id").type(JsonFieldType.STRING).description("반복 퀘스트 아이디"),
		fieldWithPath("dailyQuests[].subjectId").type(JsonFieldType.STRING).description("퀘스트 주제(관심사) 아이디"),
		fieldWithPath("dailyQuests[].questType").type(JsonFieldType.STRING).description("퀘스트 타입: DAILY / WEEKLY / MONTHLY"),
		fieldWithPath("dailyQuests[].point").type(JsonFieldType.NUMBER).description("포인트 보상"),
		fieldWithPath("dailyQuests[].score").type(JsonFieldType.NUMBER).description("점수 보상"),
		fieldWithPath("dailyQuests[].subjectName").type(JsonFieldType.STRING).description("퀘스트 주제(관심사) 이름"),
		fieldWithPath("dailyQuests[].content").type(JsonFieldType.STRING).description("퀘스트 내용"),
		fieldWithPath("dailyQuests[].displayOrder").type(JsonFieldType.NUMBER).description("정렬 순서"),

		fieldWithPath("weeklyQuests").type(JsonFieldType.ARRAY).description("주간 퀘스트 목록"),
		fieldWithPath("monthlyQuests").type(JsonFieldType.ARRAY).description("월간 퀘스트 목록")
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.responseSchema(Schema.schema("ListRepeatQuestResponse")),
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
