package com.gomo.app.streak.documentation.snippet;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import com.gomo.app.common.constant.ErrorResponseFields;

public class ListStreakSnippet {

	private static final String IDENTIFIER = "streak-list-find";

	private static final Snippet REQUEST_HEADERS = requestHeaders(
		headerWithName(AUTHORIZATION).description("JWT Access Token (Bearer)")
	);

	private static final Snippet QUERY_PARAMETERS = queryParameters(
		parameterWithName("startDate").description("조회 시작 날짜 (YYYY-MM-DD)"),
		parameterWithName("endDate").description("조회 종료 날짜 (YYYY-MM-DD)")
	);

	private static final Snippet RESPONSE_FIELDS = responseFields(getCombinedResponseFields());

	private static List<FieldDescriptor> getCombinedResponseFields() {
		List<FieldDescriptor> fields = new ArrayList<>();
		fields.add(fieldWithPath("dailyStreaks").type(JsonFieldType.ARRAY).description("일간 스트릭 목록"));
		fields.addAll(getStreakObjectFields("dailyStreaks"));

		fields.add(fieldWithPath("weeklyStreaks").type(JsonFieldType.ARRAY).description("주간 스트릭 목록"));
		fields.addAll(getStreakObjectFields("weeklyStreaks"));

		fields.add(fieldWithPath("monthlyStreaks").type(JsonFieldType.ARRAY).description("월간 스트릭 목록 (이 배열의 객체 구조는 위와 동일)"));

		return fields;
	}

	private static List<FieldDescriptor> getStreakObjectFields(String prefix) {
		return Arrays.asList(
			fieldWithPath(prefix + "[].id").type(JsonFieldType.STRING).description("스트릭 아이디"),
			fieldWithPath(prefix + "[].streakType").type(JsonFieldType.STRING).description("스트릭 타입: `DAILY`, `WEEKLY`, `MONTHLY`"),
			fieldWithPath(prefix + "[].filledDate").type(JsonFieldType.STRING).description("스트릭이 채워진 날짜 (YYYY-MM-DD)"),
			fieldWithPath(prefix + "[].completedQuestCount").type(JsonFieldType.NUMBER).description("해당 날짜에 완료한 퀘스트 개수")
		);
	}

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			preprocessRequest(prettyPrint()),
			preprocessResponse(prettyPrint()),
			REQUEST_HEADERS,
			QUERY_PARAMETERS,
			RESPONSE_FIELDS
		);
	}

	public static RestDocumentationFilter createError() {
		return document(
			IDENTIFIER + "-error",
			preprocessRequest(prettyPrint()),
			preprocessResponse(prettyPrint()),
			REQUEST_HEADERS,
			QUERY_PARAMETERS,
			ErrorResponseFields.RESPONSE_FIELDS
		);
	}
}
