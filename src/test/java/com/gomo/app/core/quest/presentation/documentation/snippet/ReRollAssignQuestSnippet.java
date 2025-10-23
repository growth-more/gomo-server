package com.gomo.app.core.quest.presentation.documentation.snippet;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import com.gomo.app.test.ErrorResponseFields;

public class ReRollAssignQuestSnippet {

	private static final String IDENTIFIER = "quest-assign-re-roll";

	private static final Snippet REQUEST_HEADERS = requestHeaders(
		headerWithName(AUTHORIZATION).description("JWT Access Token (Bearer)")
	);

	private static final Snippet REQUEST_FIELDS = requestFields(
		fieldWithPath("assignQuestId").type(JsonFieldType.STRING).description("재생성 대상 할당 퀘스트 아이디")
	);

	private static final Snippet RESPONSE_FIELDS = responseFields(
		fieldWithPath("id").type(JsonFieldType.STRING).description("할당 퀘스트 아이디"),
		fieldWithPath("subjectId").type(JsonFieldType.STRING).description("퀘스트 주제(관심사) 아이디"),
		fieldWithPath("questType").type(JsonFieldType.STRING).description("퀘스트 타입: `DAILY`, `WEEKLY`, `MONTHLY`"),
		fieldWithPath("point").type(JsonFieldType.NUMBER).description("포인트 보상"),
		fieldWithPath("score").type(JsonFieldType.NUMBER).description("경험치 보상"),
		fieldWithPath("subjectName").type(JsonFieldType.STRING).description("퀘스트 주제(관심사) 이름"),
		fieldWithPath("content").type(JsonFieldType.STRING).description("퀘스트 내용"),
		fieldWithPath("confirmed").type(JsonFieldType.BOOLEAN).description("확정 여부"),
		fieldWithPath("completed").type(JsonFieldType.BOOLEAN).description("완료 여부"),
		fieldWithPath("proof").type(JsonFieldType.STRING).optional().description("퀘스트 증명 내용 (URL, 텍스트 등, 없을 경우 null)"),
		fieldWithPath("startDateTime").type(JsonFieldType.STRING).description("퀘스트 시작일 (ISO 8601 형식)"),
		fieldWithPath("displayOrder").type(JsonFieldType.NUMBER).description("정렬 순서")
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			preprocessRequest(prettyPrint()),
			preprocessResponse(prettyPrint()),
			REQUEST_HEADERS,
			REQUEST_FIELDS,
			RESPONSE_FIELDS
		);
	}

	public static RestDocumentationFilter createError() {
		return document(
			IDENTIFIER + "-error",
			preprocessRequest(prettyPrint()),
			preprocessResponse(prettyPrint()),
			REQUEST_HEADERS,
			ErrorResponseFields.RESPONSE_FIELDS
		);
	}
}
