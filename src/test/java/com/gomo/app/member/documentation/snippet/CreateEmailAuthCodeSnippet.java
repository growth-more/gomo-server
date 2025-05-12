package com.gomo.app.member.documentation.snippet;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.gomo.app.common.constant.ErrorResponseFields;

public class CreateEmailAuthCodeSnippet {

	private static final String IDENTIFIER = "create_email_auth_code";
	private static final String SUMMARY = "이메일 인증코드 생성 API";
	private static final String DESCRIPTION = "회원가입 시 사용하는 이메일 인증 코드를 생성합니다.";
	private static final String TAG = "Member";

	private static final Snippet REQUEST_FIELDS = requestFields(
		fieldWithPath("email").type(JsonFieldType.STRING).description("회원 이메일")
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.requestSchema(Schema.schema("CreateEmailAuthCodeRequest")),
			REQUEST_FIELDS
		);
	}

	public static RestDocumentationFilter createError() {
		return document(
			IDENTIFIER + "/error",
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.requestSchema(Schema.schema("CreateEmailAuthCodeRequest"))
				.responseSchema(Schema.schema("ErrorResponse")),
			REQUEST_FIELDS,
			ErrorResponseFields.RESPONSE_FIELDS
		);

	}

}
