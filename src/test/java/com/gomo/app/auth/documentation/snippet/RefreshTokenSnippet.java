package com.gomo.app.auth.documentation.snippet;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.gomo.app.common.constant.ErrorResponseFields;

public class RefreshTokenSnippet {

	private static final String IDENTIFIER = "refresh_access_token";
	private static final String SUMMARY = "Access 토큰 재발급 API";
	private static final String DESCRIPTION = "Access Token 이 만료되었을 때, Refresh Token을 이용하여 재발급합니다.";
	private static final String TAG = "Member";

	private static final Snippet RESPONSE_FIELDS = responseFields(
		fieldWithPath("memberId").type(JsonFieldType.STRING).description("Member ID"),
		fieldWithPath("token").type(JsonFieldType.STRING).description("AccessToken")
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.responseSchema(Schema.schema("TokenResponse")),
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
