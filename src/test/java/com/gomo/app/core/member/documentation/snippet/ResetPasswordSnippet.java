package com.gomo.app.core.member.documentation.snippet;

import static com.gomo.app.test.ErrorResponseFields.*;
import static org.springframework.http.HttpHeaders.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

public class ResetPasswordSnippet {
	private static final String IDENTIFIER = "member-password-reset";

	private static final Snippet REQUEST_HEADERS = requestHeaders(
		headerWithName(CONTENT_TYPE).description("Content-Type: `application/json`")
	);

	private static final Snippet REQUEST_FIELDS = requestFields(
		fieldWithPath("email").type(JsonFieldType.STRING).description("비밀번호를 초기화할 계정의 이메일 주소"),
		fieldWithPath("newPassword").type(JsonFieldType.STRING).description("새로운 비밀번호"),
		fieldWithPath("temporaryToken").type(JsonFieldType.STRING).description("인증된 이메일임을 확인하기 위한 임시 코드")
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
			RESPONSE_FIELDS
		);
	}
}
