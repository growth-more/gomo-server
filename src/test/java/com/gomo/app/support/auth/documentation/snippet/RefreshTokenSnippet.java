package com.gomo.app.support.auth.documentation.snippet;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.restdocs.cookies.CookieDocumentation.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import com.gomo.app.test.ErrorResponseFields;

public class RefreshTokenSnippet {

	private static final String IDENTIFIER = "auth-update-refresh-token";

	private static final Snippet REQUEST_HEADERS = requestHeaders(
		headerWithName(CONTENT_TYPE).description("Content-Type: `application/json` (요청 본문은 비어있음)")
	);

	private static final Snippet REQUEST_COOKIES = requestCookies(
		cookieWithName("refreshToken").description("Access Token 재발급에 사용되는 Refresh Token")
	);

	private static final Snippet RESPONSE_FIELDS = responseFields(
		fieldWithPath("principalId").type(JsonFieldType.STRING).description("회원 식별자 ID (UUID)"),
		fieldWithPath("accessToken").type(JsonFieldType.STRING).description("새로 발급된 JWT Access Token")
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			preprocessRequest(prettyPrint()),
			preprocessResponse(prettyPrint()),
			REQUEST_HEADERS,
			REQUEST_COOKIES,
			RESPONSE_FIELDS
		);
	}

	public static RestDocumentationFilter createError() {
		return document(
			IDENTIFIER + "-error",
			preprocessRequest(prettyPrint()),
			preprocessResponse(prettyPrint()),
			REQUEST_HEADERS,
			REQUEST_COOKIES,
			ErrorResponseFields.RESPONSE_FIELDS
		);
	}
}
