package com.gomo.app.quest.documentation.snippet;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.gomo.app.common.constant.ErrorResponseFields;

public class ListAssignQuestSnippet {

	private static final String IDENTIFIER = "list_assign_quest";
	private static final String SUMMARY = "할당 퀘스트 목록 조회 API";
	private static final String DESCRIPTION = "사용자가 할당 퀘스트 목록을 조회합니다.";
	private static final String TAG = "Quest";	

	private static final Snippet RESPONSE_FIELDS = responseFields(
		fieldWithPath("assignQuests").type(JsonFieldType.ARRAY).description("할당 퀘스트 목록"),
		fieldWithPath("assignQuests[].id").type(JsonFieldType.STRING).description("할당 퀘스트 아이디"),
		fieldWithPath("assignQuests[].subjectId").type(JsonFieldType.STRING).description("퀘스트 주제(관심사) 아이디"),
		fieldWithPath("assignQuests[].questType").type(JsonFieldType.STRING).description("퀘스트 타입: DAILY / WEEKLY / MONTHLY"),
		fieldWithPath("assignQuests[].point").type(JsonFieldType.NUMBER).description("포인트 보상"),
		fieldWithPath("assignQuests[].score").type(JsonFieldType.NUMBER).description("점수 보상"),
		fieldWithPath("assignQuests[].subjectName").type(JsonFieldType.STRING).description("퀘스트 주제(관심사) 이름"),
		fieldWithPath("assignQuests[].content").type(JsonFieldType.STRING).description("퀘스트 내용"),
		fieldWithPath("assignQuests[].isConfirmed").type(JsonFieldType.STRING).description("확정 여부"),
		fieldWithPath("assignQuests[].isCompleted").type(JsonFieldType.STRING).description("완료 여부"),
		fieldWithPath("assignQuests[].proofUrl").type(JsonFieldType.STRING).description("퀘스트 증명"),
		fieldWithPath("assignQuests[].startDateTime").type(JsonFieldType.STRING).description("퀘스트 시작일"),
		fieldWithPath("assignQuests[].displayOrder").type(JsonFieldType.STRING).description("정렬 순서")
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.responseSchema(Schema.schema("ReadAssignQuestResponse")),
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
