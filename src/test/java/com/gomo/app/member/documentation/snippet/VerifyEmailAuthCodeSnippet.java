package com.gomo.app.member.documentation.snippet;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.gomo.app.common.constant.ErrorResponseFields;

public class VerifyEmailAuthCodeSnippet {
	private static final String IDENTIFIER = "verify_email_auth_code";
	private static final String SUMMARY = "이메일 인증코드 검증 API";
	private static final String DESCRIPTION = "회원가입 시 사용하는 이메일 인증 코드를 검증합니다.";
	private static final String TAG = "Member";

	private static final ParameterDescriptor[] REQUEST_PARAMETERS = {
		parameterWithName("email").description("회원 이메일"),
		parameterWithName("code").description("이메일 인증 코드"),
	};

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.queryParameters(REQUEST_PARAMETERS),
			queryParameters(REQUEST_PARAMETERS)
		);
	}

	public static RestDocumentationFilter createError() {
		return document(
			IDENTIFIER + "/error",
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.queryParameters(REQUEST_PARAMETERS)
				.responseSchema(Schema.schema("ErrorResponse")),
			queryParameters(REQUEST_PARAMETERS),
			ErrorResponseFields.RESPONSE_FIELDS
		);
	}
}
