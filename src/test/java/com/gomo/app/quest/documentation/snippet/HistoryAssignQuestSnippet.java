package com.gomo.app.quest.documentation.snippet;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.gomo.app.common.constant.ErrorResponseFields;

public class HistoryAssignQuestSnippet {

	private static final String IDENTIFIER = "history_assign_quest";
	private static final String SUMMARY = "할당 퀘스트 과거 이력 조회 API";
	private static final String DESCRIPTION = "사용자의 과거 할당 퀘스트 수행 이력을 조회합니다.";
	private static final String TAG = "Quest";

	private static final Snippet RESPONSE_FIELDS = responseFields(
		fieldWithPath("dailyHistoryQuests").type(JsonFieldType.ARRAY).description("일일 퀘스트 과거 이력"),
		fieldWithPath("dailyHistoryQuests[].id").type(JsonFieldType.STRING).description("할당 퀘스트 아이디"),
		fieldWithPath("dailyHistoryQuests[].questType").type(JsonFieldType.STRING).description("퀘스트 타입"),
		fieldWithPath("dailyHistoryQuests[].subjectName").type(JsonFieldType.STRING).description("주제(관심사) 이름"),
		fieldWithPath("dailyHistoryQuests[].content").type(JsonFieldType.STRING).description("퀘스트 내용"),
		fieldWithPath("dailyHistoryQuests[].proof").type(JsonFieldType.STRING).description("퀘스트 증명"),
		fieldWithPath("dailyHistoryQuests[].completed").type(JsonFieldType.BOOLEAN).description("완료 여부"),
		fieldWithPath("dailyHistoryQuests[].completedDateTime").type(JsonFieldType.STRING).optional().description("완료일 (없을 경우 null)"),

		fieldWithPath("weeklyHistoryQuests").type(JsonFieldType.ARRAY).description("주간 퀘스트 과거 이력"),
		fieldWithPath("weeklyHistoryQuests[].id").type(JsonFieldType.STRING).description("할당 퀘스트 아이디"),
		fieldWithPath("weeklyHistoryQuests[].questType").type(JsonFieldType.STRING).description("퀘스트 타입"),
		fieldWithPath("weeklyHistoryQuests[].subjectName").type(JsonFieldType.STRING).description("주제(관심사) 이름"),
		fieldWithPath("weeklyHistoryQuests[].content").type(JsonFieldType.STRING).description("퀘스트 내용"),
		fieldWithPath("weeklyHistoryQuests[].proof").type(JsonFieldType.STRING).description("퀘스트 증명"),
		fieldWithPath("weeklyHistoryQuests[].completed").type(JsonFieldType.BOOLEAN).description("완료 여부"),
		fieldWithPath("weeklyHistoryQuests[].completedDateTime").type(JsonFieldType.STRING).optional().description("완료일 (없을 경우 null)"),

		fieldWithPath("monthlyHistoryQuests").type(JsonFieldType.ARRAY).description("월간 퀘스트 과거 이력"),
		fieldWithPath("monthlyHistoryQuests[].id").type(JsonFieldType.STRING).description("할당 퀘스트 아이디"),
		fieldWithPath("monthlyHistoryQuests[].questType").type(JsonFieldType.STRING).description("퀘스트 타입"),
		fieldWithPath("monthlyHistoryQuests[].subjectName").type(JsonFieldType.STRING).description("주제(관심사) 이름"),
		fieldWithPath("monthlyHistoryQuests[].content").type(JsonFieldType.STRING).description("퀘스트 내용"),
		fieldWithPath("monthlyHistoryQuests[].proof").type(JsonFieldType.STRING).description("퀘스트 증명"),
		fieldWithPath("monthlyHistoryQuests[].completed").type(JsonFieldType.BOOLEAN).description("완료 여부"),
		fieldWithPath("monthlyHistoryQuests[].completedDateTime").type(JsonFieldType.STRING).optional().description("완료일 (없을 경우 null)")
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.responseSchema(Schema.schema("HistoryListAssignQuestResponse")),
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
