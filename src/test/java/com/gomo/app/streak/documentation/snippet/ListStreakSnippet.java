package com.gomo.app.streak.documentation.snippet;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.gomo.app.common.constant.ErrorResponseFields;

public class ListStreakSnippet {

	private static final String IDENTIFIER = "list_streak";
	private static final String SUMMARY = "스트릭 목록 조회 API";
	private static final String DESCRIPTION = "사용자의 스트릭 목록을 조회합니다.";
	private static final String TAG = "Streak";

	private static final Snippet RESPONSE_FIELDS = responseFields(
		fieldWithPath("streakType").type(JsonFieldType.ARRAY).description("스트릭 타입: DAILY / WEEKLY / MONTHLY"),
		fieldWithPath("streaks").type(JsonFieldType.ARRAY).description("스트릭 목록"),
		fieldWithPath("streaks[].id").type(JsonFieldType.STRING).description("스트릭 아이디"),
		fieldWithPath("streaks[].filledDateTime").type(JsonFieldType.STRING).description("스트릭 생성 날짜"),
		fieldWithPath("streaks[].weekOfYear").type(JsonFieldType.NUMBER).description("스트릭 생성 주차: 주간 스트릭 타입만 해당"),
		fieldWithPath("streaks[].completedQuestCount").type(JsonFieldType.NUMBER).description("완료한 퀘스트 개수")
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.responseSchema(Schema.schema("ListStreakResponse")),
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
