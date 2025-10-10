package com.gomo.app.core.member.documentation.snippet;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import com.gomo.app.test.ErrorResponseFields;

public class UpdateMemberSnippet {

	private static final String IDENTIFIER = "member-info-update";

	private static final Snippet REQUEST_HEADERS = requestHeaders(
		headerWithName(CONTENT_TYPE).description("Content-Type: `application/json`"),
		headerWithName(AUTHORIZATION).description("JWT Access Token (Bearer)")
	);

	private static final Snippet REQUEST_FIELDS = requestFields(
		fieldWithPath("name").type(JsonFieldType.STRING).description("새로운 사용자 이름"),
		fieldWithPath("motto").type(JsonFieldType.STRING).description("새로운 좌우명 또는 한 줄 소개")
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			preprocessRequest(prettyPrint()),
			preprocessResponse(prettyPrint()),
			REQUEST_HEADERS,
			REQUEST_FIELDS
		);
	}

	public static RestDocumentationFilter createError() {
		return document(
			IDENTIFIER + "-error",
			preprocessRequest(prettyPrint()),
			preprocessResponse(prettyPrint()),
			REQUEST_HEADERS,
			REQUEST_FIELDS,
			ErrorResponseFields.RESPONSE_FIELDS
		);
	}
}
