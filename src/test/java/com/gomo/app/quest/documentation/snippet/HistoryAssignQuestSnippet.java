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
		fieldWithPath("histories").type(JsonFieldType.ARRAY).description("할당 퀘스트 과거 이력"),
		fieldWithPath("histories[].id").type(JsonFieldType.STRING).description("할당 퀘스트 아이디"),
		fieldWithPath("histories[].questType").type(JsonFieldType.STRING).description("퀘스트 타입"),
		fieldWithPath("histories[].interestName").type(JsonFieldType.STRING).description("관심사 이름"),
		fieldWithPath("histories[].content").type(JsonFieldType.STRING).description("퀘스트 내용"),
		fieldWithPath("histories[].proofUrl").type(JsonFieldType.STRING).description("퀘스트 증명"),
		fieldWithPath("histories[].isCompleted").type(JsonFieldType.STRING).description("완료 여부"),
		fieldWithPath("histories[].completedDateTime").type(JsonFieldType.STRING).description("완료일"),
		fieldWithPath("histories[].weekOfYear").type(JsonFieldType.NUMBER).description("퀘스트를 할당받은 주차")
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
