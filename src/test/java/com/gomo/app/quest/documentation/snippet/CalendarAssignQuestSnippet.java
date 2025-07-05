package com.gomo.app.quest.documentation.snippet;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import com.gomo.app.common.constant.ErrorResponseFields;

public class CalendarAssignQuestSnippet {

	private static final String IDENTIFIER = "quest-assign-calendar-find";

	private static final Snippet REQUEST_HEADERS = requestHeaders(
		headerWithName(AUTHORIZATION).description("JWT Access Token (Bearer)")
	);

	private static final Snippet QUERY_PARAMETERS = queryParameters(
		parameterWithName("year").description("조회할 년도 (예: 2024)"),
		parameterWithName("month").description("조회할 월 (1~12)"),
		parameterWithName("day").description("조회 기준일 (1~31)"),
		parameterWithName("periodType").description("조회 기간 단위: `DAY`, `MONTH`")
	);

	private static final Snippet RESPONSE_FIELDS = responseFields(
		fieldWithPath("assignQuests").type(JsonFieldType.ARRAY).description("할당 퀘스트 목록"),
		fieldWithPath("assignQuests[].id").type(JsonFieldType.STRING).description("할당 퀘스트 아이디"),
		fieldWithPath("assignQuests[].questType").type(JsonFieldType.STRING).description("퀘스트 타입: `DAILY`, `WEEKLY`, `MONTHLY`"),
		fieldWithPath("assignQuests[].subjectName").type(JsonFieldType.STRING).description("주제(관심사) 이름"),
		fieldWithPath("assignQuests[].content").type(JsonFieldType.STRING).description("퀘스트 내용"),
		fieldWithPath("assignQuests[].proof").type(JsonFieldType.STRING).description("퀘스트 완료에 대한 증명 내용 (URL, 텍스트 등)"),
		fieldWithPath("assignQuests[].completed").type(JsonFieldType.BOOLEAN).description("완료 여부"),
		fieldWithPath("assignQuests[].completedDateTime").type(JsonFieldType.STRING).optional().description("완료 일시 (ISO 8601 형식, 완료되지 않았을 경우 null)")
	);

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
