package com.gomo.app.auth.documentation.snippet;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.*;

import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import com.gomo.app.common.constant.ErrorResponseFields;

public class VerifyEmailAuthCodeSnippet {
	private static final String IDENTIFIER = "member-email-auth-code-verify";

	private static final Snippet QUERY_PARAMETERS = queryParameters(
		parameterWithName("email").description("인증 코드를 받은 이메일 주소"),
		parameterWithName("code").description("이메일로 발송된 인증 코드")
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			preprocessRequest(prettyPrint()),
			preprocessResponse(prettyPrint()),
			QUERY_PARAMETERS
		);
	}

	public static RestDocumentationFilter createError() {
		return document(
			IDENTIFIER + "-error",
			preprocessRequest(prettyPrint()),
			preprocessResponse(prettyPrint()),
			QUERY_PARAMETERS,
			ErrorResponseFields.RESPONSE_FIELDS
		);
	}
}
