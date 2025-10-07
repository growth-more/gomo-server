package com.gomo.app.core.interest.presentation.documentation.snippet;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.*;

import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import com.gomo.app.common.constant.ErrorResponseFields;

public class InterestNetworkSnippet {

	private static final String IDENTIFIER = "interest-network-find";

	private static final Snippet REQUEST_HEADERS = requestHeaders(
		headerWithName(AUTHORIZATION).description("JWT Access Token (Bearer)")
	);

	private static final Snippet RESPONSE_FIELDS = responseFields(
		fieldWithPath("interests").type(ARRAY).description("관심사 목록"),
		fieldWithPath("interests[].id").type(STRING).description("관심사 아이디"),
		fieldWithPath("interests[].registrantId").type(STRING).description("사용자 아이디"),
		fieldWithPath("interests[].name").type(STRING).description("관심사 이름"),
		fieldWithPath("interests[].logoUrl").type(STRING).description("관심사 로고 이미지 URL"),
		fieldWithPath("interests[].colorCode").type(STRING).description("관심사 색상 코드"),
		fieldWithPath("interests[].level").type(NUMBER).description("레벨"),
		fieldWithPath("interests[].score").type(NUMBER).description("현재 레벨의 경험치"),
		fieldWithPath("interests[].scoreThreshold").type(NUMBER).description("다음 레벨업에 필요한 경험치"),
		fieldWithPath("interests[].totalScore").type(NUMBER).description("누적 경험치"),
		fieldWithPath("interests[].majorInterestId").type(STRING).description("주요 관심사 아이디").optional(),

		fieldWithPath("relations").type(ARRAY).description("관심사 간의 연결 관계 목록"),
		fieldWithPath("relations[].id").type(STRING).description("관심사 간선 아이디"),
		fieldWithPath("relations[].registrantId").type(STRING).description("사용자 아이디"),
		fieldWithPath("relations[].parentInterestId").type(STRING).description("상위 관심사 아이디"),
		fieldWithPath("relations[].childInterestId").type(STRING).description("하위 관심사 아이디")
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			preprocessRequest(prettyPrint()),
			preprocessResponse(prettyPrint()),
			REQUEST_HEADERS,
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
