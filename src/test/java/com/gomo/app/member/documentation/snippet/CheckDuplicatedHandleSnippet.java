package com.gomo.app.member.documentation.snippet;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;

import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.gomo.app.common.constant.ErrorResponseFields;

public class CheckDuplicatedHandleSnippet {

	private static final String IDENTIFIER = "check_duplicated_handle";
	private static final String SUMMARY = "핸들 중복 확인 API";
	private static final String DESCRIPTION = "핸들 중복 및 유효성을 체크하고 상태 코드를 반환합니다.";
	private static final String TAG = "Member";

	private static final ParameterDescriptor[] CHECK_DUPLICATED_HANDLE_QUERY_PARAMETERS = {
		parameterWithName("handle").description("핸들")
	};

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG),
			queryParameters(CHECK_DUPLICATED_HANDLE_QUERY_PARAMETERS)
		);
	}

	public static RestDocumentationFilter createError() {
		return document(
			IDENTIFIER + "/error",
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG),
			ErrorResponseFields.RESPONSE_FIELDS,
			queryParameters(CHECK_DUPLICATED_HANDLE_QUERY_PARAMETERS)
		);
	}
}
