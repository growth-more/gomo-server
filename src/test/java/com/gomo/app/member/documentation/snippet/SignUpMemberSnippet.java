package com.gomo.app.member.documentation.snippet;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.gomo.app.common.constant.ErrorResponseFields;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;

public class SignUpMemberSnippet {

	private static final String IDENTIFIER = "sign_up_member";
	private static final String SUMMARY = "회원가입 API";
	private static final String DESCRIPTION = "사용자 회원가입을 처리합니다.";
	private static final String TAG = "Member";

	private static final Snippet REQUEST_FIELDS = requestFields(
		fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
		fieldWithPath("password").type(JsonFieldType.STRING).description("비밀번호"),
		fieldWithPath("handle").type(JsonFieldType.STRING).description("사용자가 지정한 고유 식별 문자"),
		fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
		fieldWithPath("motto").type(JsonFieldType.STRING).description("한 줄 다짐")
	);

	private static final Snippet RESPONSE_FIELDS = responseFields(
		fieldWithPath("id").type(JsonFieldType.STRING).description("사용자 아이디: 임의의 고유 식별 문자")
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.requestSchema(Schema.schema("CreateMemberRequest"))
				.responseSchema(Schema.schema("CreateMemberResponse")),
			REQUEST_FIELDS,
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
				.requestSchema(Schema.schema("CreateMemberRequest"))
				.responseSchema(Schema.schema("ErrorResponse")),
			REQUEST_FIELDS,
			ErrorResponseFields.RESPONSE_FIELDS
		);
	}
}
