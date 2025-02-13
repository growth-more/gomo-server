package com.gomo.app.streak.documentation.snippet;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;
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

	private static final ParameterDescriptor[] LIST_STREAK_QUERY_PARAMETERS = {
		parameterWithName("startDate").description("시작 날짜"),
		parameterWithName("endDate").description("끝 날짜")
	};

	private static final Snippet RESPONSE_FIELDS = responseFields(
		fieldWithPath("dailyStreaks").type(JsonFieldType.ARRAY).description("일간 스트릭 목록"),
		fieldWithPath("dailyStreaks[].id").type(JsonFieldType.STRING).description("스트릭 아이디"),
		fieldWithPath("dailyStreaks[].streakType").type(JsonFieldType.STRING).description("스트릭 타입"),
		fieldWithPath("dailyStreaks[].filledDate").type(JsonFieldType.STRING).description("스트릭 생성 날짜"),
		fieldWithPath("dailyStreaks[].completedQuestCount").type(JsonFieldType.NUMBER).description("완료한 퀘스트 개수"),

		fieldWithPath("weeklyStreaks").type(JsonFieldType.ARRAY).description("주간 스트릭 목록"),
		fieldWithPath("weeklyStreaks[].id").type(JsonFieldType.STRING).description("스트릭 아이디"),
		fieldWithPath("weeklyStreaks[].streakType").type(JsonFieldType.STRING).description("스트릭 타입"),
		fieldWithPath("weeklyStreaks[].filledDate").type(JsonFieldType.STRING).description("스트릭 생성 날짜"),
		fieldWithPath("weeklyStreaks[].completedQuestCount").type(JsonFieldType.NUMBER).description("완료한 퀘스트 개수"),

		fieldWithPath("monthlyStreaks").type(JsonFieldType.ARRAY).description("월간 스트릭 목록")
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.queryParameters(LIST_STREAK_QUERY_PARAMETERS)
				.responseSchema(Schema.schema("ListStreakResponse")),
			RESPONSE_FIELDS,
			queryParameters(LIST_STREAK_QUERY_PARAMETERS)
		);
	}

	public static RestDocumentationFilter createError() {
		return document(
			IDENTIFIER + "/error",
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.queryParameters(LIST_STREAK_QUERY_PARAMETERS)
				.responseSchema(Schema.schema("ErrorResponse")),
			ErrorResponseFields.RESPONSE_FIELDS,
			queryParameters(LIST_STREAK_QUERY_PARAMETERS)
		);
	}
}
