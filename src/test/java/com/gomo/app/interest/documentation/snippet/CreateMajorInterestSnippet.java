package com.gomo.app.interest.documentation.snippet;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.gomo.app.common.constant.ErrorResponseFields;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import java.util.Arrays;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;

public class CreateMajorInterestSnippet {

	private static final String IDENTIFIER = "create_major_interest";
	private static final String SUMMARY = "주요 관심사 등록 API";
	private static final String DESCRIPTION = "사용자의 주요 관심사를 등록합니다.";
	private static final String TAG = "Interest";

	private static final ParameterDescriptor[] PATH_PARAMETERS = {
		parameterWithName("id").description("관심사 ID")
	};

	private static final FieldDescriptor[] RESPONSE_FIELDS2 = {
		fieldWithPath("id").type(JsonFieldType.STRING).description("주요 관심사 아이디")
	};

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.pathParameters(PATH_PARAMETERS)
				.responseSchema(Schema.schema("CreateMajorInterestResponse")),
			preprocessRequest(prettyPrint()),
			preprocessResponse(prettyPrint()),
			pathParameters(PATH_PARAMETERS),
			responseFields(Arrays.stream(RESPONSE_FIELDS2).toList())
		);
	}

	public static RestDocumentationFilter createError() {
		return document(
			IDENTIFIER + "/error",
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.pathParameters(PATH_PARAMETERS)
				.responseSchema(Schema.schema("ErrorResponse")),
			pathParameters(PATH_PARAMETERS),
			ErrorResponseFields.RESPONSE_FIELDS
		);
	}
}
