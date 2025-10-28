package com.gomo.app.core.interest.adapter.in.api.snippet;

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

public class CreateInterestSnippet {

	private static final String IDENTIFIER = "interest-create";

	private static final Snippet REQUEST_HEADERS = requestHeaders(
		headerWithName(CONTENT_TYPE).description("Content-Type: `multipart/form-data`"),
		headerWithName(AUTHORIZATION).description("JWT Access Token (Bearer)")
	);

	private static final Snippet REQUEST_PARTS = requestParts(
		partWithName("name").description("관심사 이름"),
		partWithName("colorCode").description("색상 코드 (ex: #0000FF)"),
		partWithName("logoFile").description("로고 이미지 파일")
	);

	private static final Snippet RESPONSE_FIELDS = responseFields(
		fieldWithPath("id").type(STRING).description("생성된 관심사의 고유 ID")
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			preprocessRequest(prettyPrint()),
			preprocessResponse(prettyPrint()),
			REQUEST_HEADERS,
			REQUEST_PARTS,
			RESPONSE_FIELDS
		);
	}

	public static RestDocumentationFilter createError() {
		return document(
			IDENTIFIER + "-error",
			preprocessRequest(prettyPrint()),
			preprocessResponse(prettyPrint()),
			REQUEST_HEADERS,
			REQUEST_PARTS,
			ErrorResponseFields.RESPONSE_FIELDS
		);
	}
}
