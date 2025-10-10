package com.gomo.app.core.interest.presentation.documentation.snippet;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.*;

import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import com.gomo.app.test.ErrorResponseFields;

public class CreateMajorInterestSnippet {

	private static final String IDENTIFIER = "major-interest-create";

	private static final Snippet REQUEST_HEADERS = requestHeaders(
		headerWithName(AUTHORIZATION).description("JWT Access Token (Bearer)")
	);

	private static final Snippet PATH_PARAMETERS = pathParameters(
		parameterWithName("id").description("주요 관심사로 등록할 관심사의 ID (UUID)")
	);

	private static final Snippet RESPONSE_FIELDS = responseFields(
		fieldWithPath("id").type(STRING).description("생성된 주요 관심사 데이터의 고유 ID (UUID)")
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			preprocessRequest(prettyPrint()),
			preprocessResponse(prettyPrint()),
			REQUEST_HEADERS,
			PATH_PARAMETERS,
			RESPONSE_FIELDS
		);
	}

	public static RestDocumentationFilter createError() {
		return document(
			IDENTIFIER + "-error",
			preprocessRequest(prettyPrint()),
			preprocessResponse(prettyPrint()),
			REQUEST_HEADERS,
			PATH_PARAMETERS,
			ErrorResponseFields.RESPONSE_FIELDS
		);
	}
}
