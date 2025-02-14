package com.gomo.app.quest.documentation.snippet;

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

public class CalendarAssignQuestSnippet {

	private static final String IDENTIFIER = "calendar_assign_quest";
	private static final String SUMMARY = "할당 퀘스트 캘린더 조회 API";
	private static final String DESCRIPTION = "사용자가 한 달간 수행한, 혹은 수행 중인 퀘스트 목록을 조회합니다.";
	private static final String TAG = "Quest";

	private static final ParameterDescriptor[] CALENDAR_ASSIGN_QUEST_QUERY_PARAMETERS = {
		parameterWithName("year").description("년"),
		parameterWithName("month").description("월")
	};

	private static final Snippet RESPONSE_FIELDS = responseFields(
		fieldWithPath("assignQuests").type(JsonFieldType.ARRAY).description("할당 퀘스트 목록"),
		fieldWithPath("assignQuests[].id").type(JsonFieldType.STRING).description("할당 퀘스트 아이디"),
		fieldWithPath("assignQuests[].questType").type(JsonFieldType.STRING).description("퀘스트 타입"),
		fieldWithPath("assignQuests[].subjectName").type(JsonFieldType.STRING).description("주제(관심사) 이름"),
		fieldWithPath("assignQuests[].content").type(JsonFieldType.STRING).description("퀘스트 내용"),
		fieldWithPath("assignQuests[].proof").type(JsonFieldType.STRING).description("퀘스트 증명"),
		fieldWithPath("assignQuests[].completed").type(JsonFieldType.BOOLEAN).description("완료 여부"),
		fieldWithPath("assignQuests[].completedDateTime").type(JsonFieldType.STRING).optional().description("완료일 (없을 경우 null)")

	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.queryParameters(CALENDAR_ASSIGN_QUEST_QUERY_PARAMETERS)
				.responseSchema(Schema.schema("CalendarListAssignQuestResponse")),
			RESPONSE_FIELDS,
			queryParameters(CALENDAR_ASSIGN_QUEST_QUERY_PARAMETERS)
		);
	}

	public static RestDocumentationFilter createError() {
		return document(
			IDENTIFIER + "/error",
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.queryParameters(CALENDAR_ASSIGN_QUEST_QUERY_PARAMETERS)
				.responseSchema(Schema.schema("ErrorResponse")),
			ErrorResponseFields.RESPONSE_FIELDS,
			queryParameters(CALENDAR_ASSIGN_QUEST_QUERY_PARAMETERS)
		);
	}
}
