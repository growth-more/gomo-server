package com.gomo.app.quest.documentation.snippet;

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

public class ListRepeatQuestSnippet {

	private static final String IDENTIFIER = "quest-repeat-list";

	private static final Snippet REQUEST_HEADERS = requestHeaders(
		headerWithName(AUTHORIZATION).description("JWT Access Token (Bearer)")
	);

	private static final Snippet QUERY_PARAMETERS = queryParameters(
		parameterWithName("questType").description("조회할 퀘스트 타입 (예: DAILY, WEEKLY, MONTHLY). 미지정 시 전체 조회.").optional()
	);

	private static final Snippet RESPONSE_FIELDS = responseFields(getCombinedResponseFields());

	private static List<FieldDescriptor> getCombinedResponseFields() {
		List<FieldDescriptor> fields = new ArrayList<>();
		fields.add(fieldWithPath("dailyQuests").type(JsonFieldType.ARRAY).description("일간 반복 퀘스트 목록"));
		fields.addAll(getRepeatQuestObjectFields("dailyQuests"));

		fields.add(fieldWithPath("weeklyQuests").type(JsonFieldType.ARRAY).description("주간 반복 퀘스트 목록"));
		fields.addAll(getRepeatQuestObjectFields("weeklyQuests"));

		fields.add(fieldWithPath("monthlyQuests").type(JsonFieldType.ARRAY).description("월간 반복 퀘스트 목록"));
		fields.addAll(getRepeatQuestObjectFields("monthlyQuests"));

		return fields;
	}

	private static List<FieldDescriptor> getRepeatQuestObjectFields(String prefix) {
		return Arrays.asList(
			fieldWithPath(prefix + "[].id").type(JsonFieldType.STRING).description("반복 퀘스트 아이디"),
			fieldWithPath(prefix + "[].subjectId").type(JsonFieldType.STRING).description("퀘스트 주제(관심사) 아이디"),
			fieldWithPath(prefix + "[].questType").type(JsonFieldType.STRING).description("퀘스트 타입: `DAILY`, `WEEKLY`, `MONTHLY`"),
			fieldWithPath(prefix + "[].point").type(JsonFieldType.NUMBER).description("포인트 보상"),
			fieldWithPath(prefix + "[].score").type(JsonFieldType.NUMBER).description("경험치 보상"),
			fieldWithPath(prefix + "[].subjectName").type(JsonFieldType.STRING).description("퀘스트 주제(관심사) 이름"),
			fieldWithPath(prefix + "[].content").type(JsonFieldType.STRING).description("퀘스트 내용"),
			fieldWithPath(prefix + "[].displayOrder").type(JsonFieldType.NUMBER).description("정렬 순서")
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
