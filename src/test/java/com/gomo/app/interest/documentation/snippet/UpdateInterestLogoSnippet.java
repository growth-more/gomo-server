package com.gomo.app.interest.documentation.snippet;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.*;

import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import com.gomo.app.common.constant.ErrorResponseFields;

public class UpdateInterestLogoSnippet {

	private static final String IDENTIFIER = "interest-logo-update";

	private static final Snippet REQUEST_HEADERS = requestHeaders(
		headerWithName(CONTENT_TYPE).description("Content-Type: `multipart/form-data`"),
		headerWithName(AUTHORIZATION).description("JWT Access Token (Bearer)")
	);

	private static final Snippet PATH_PARAMETERS = pathParameters(
		parameterWithName("id").description("로고를 수정할 관심사의 ID (UUID)")
	);

	private static final Snippet REQUEST_PARTS = requestParts(
		partWithName("updatedLogo").description("새로운 로고 이미지 파일")
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			preprocessRequest(prettyPrint()),
			preprocessResponse(prettyPrint()),
			REQUEST_HEADERS,
			PATH_PARAMETERS,
			REQUEST_PARTS
		);
	}

	public static RestDocumentationFilter createError() {
		return document(
			IDENTIFIER + "-error",
			preprocessRequest(prettyPrint()),
			preprocessResponse(prettyPrint()),
			REQUEST_HEADERS,
			PATH_PARAMETERS,
			REQUEST_PARTS,
			ErrorResponseFields.RESPONSE_FIELDS
		);
	}
}
