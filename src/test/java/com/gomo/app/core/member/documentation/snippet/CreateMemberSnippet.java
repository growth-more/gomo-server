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

public class CreateMemberSnippet {
	private static final String IDENTIFIER = "member-create";

	private static final Snippet REQUEST_HEADERS = requestHeaders(
		headerWithName(CONTENT_TYPE).description("Content-Type: `application/json`")
	);

	private static final Snippet REQUEST_FIELDS = requestFields(
		fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일"),
		fieldWithPath("rawPassword").type(JsonFieldType.STRING).description("비밀번호"),
		fieldWithPath("handle").type(JsonFieldType.STRING).description("사용자 핸들 (고유 식별자, 예: @myhandle)"),
		fieldWithPath("name").type(JsonFieldType.STRING).description("사용자 이름"),
		fieldWithPath("motto").type(JsonFieldType.STRING).description("좌우명 또는 한 줄 소개"),
		fieldWithPath("loginProvider").type(JsonFieldType.STRING).description("회원가입 시 사용한 로그인 제공자 예: EMAIL, GOOGLE, KAKAO"),
		fieldWithPath("temporaryToken").type(JsonFieldType.STRING).description("인증된 이메일임을 확인하기 위한 임시 코드")
	);

	private static final Snippet RESPONSE_FIELDS = responseFields(
		fieldWithPath("id").type(JsonFieldType.STRING).description("생성된 사용자의 고유 ID (UUID)")
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
			REQUEST_FIELDS,
			ErrorResponseFields.RESPONSE_FIELDS
		);
	}
}
