package com.gomo.app.member.documentation.snippet;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.gomo.app.common.constant.ErrorResponseFields;

public class CheckHandleDuplicateSnippet {
	private static final String IDENTIFIER = "check_handle_duplicate";
	private static final String SUMMARY = "핸들 중복 체크 API";
	private static final String DESCRIPTION = "회원가입 시 사용하는 핸들의 중복 여부를 체크합니다.";
	private static final String TAG = "Member";

	private static final ParameterDescriptor[] REQUEST_PARAMETERS = {
		parameterWithName("handle").description("사용하고자 하는 핸들"),
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