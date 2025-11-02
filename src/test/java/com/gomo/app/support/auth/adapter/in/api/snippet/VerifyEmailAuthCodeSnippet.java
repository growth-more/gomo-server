package com.gomo.app.support.auth.adapter.in.api.snippet;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import com.gomo.app.test.ErrorResponseFields;

public class VerifyEmailAuthCodeSnippet {
	private static final String IDENTIFIER = "auth-code-verify";

	private static final Snippet REQUEST_FIELDS = requestFields(
		fieldWithPath("email").description("인증 코드를 받은 이메일 주소"),
		fieldWithPath("code").description("이메일로 발송된 인증 코드")
	);

	private static final Snippet RESPONSE_FIELDS = responseFields(
		fieldWithPath("temporaryToken").type(JsonFieldType.STRING).description("인증된 이메일임을 확인하기 위한 임시 토큰")
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			preprocessRequest(prettyPrint()),
			preprocessResponse(prettyPrint()),
			REQUEST_FIELDS,
			RESPONSE_FIELDS
		);
	}

	public static RestDocumentationFilter createError() {
		return document(
			IDENTIFIER + "-error",
			preprocessRequest(prettyPrint()),
			preprocessResponse(prettyPrint()),
			REQUEST_FIELDS,
			ErrorResponseFields.RESPONSE_FIELDS
		);
	}
}
