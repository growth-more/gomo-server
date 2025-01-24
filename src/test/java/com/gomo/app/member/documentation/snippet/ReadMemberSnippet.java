package com.gomo.app.member.documentation.snippet;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;

public class ReadMemberSnippet {

	private static final String IDENTIFIER = "read_member";
	private static final String SUMMARY = "사용자 프로필 조회 API";
	private static final String DESCRIPTION = "사용자의 프로필 정보를 조회합니다.";
	private static final String TAG = "Member";

	private static final Snippet RESPONSE_FIELDS = responseFields(
		fieldWithPath("id").type(JsonFieldType.STRING).description("임의의 고유 식별 문자"),
		fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
		fieldWithPath("handle").type(JsonFieldType.STRING).description("사용자가 지정한 고유 식별 문자"),
		fieldWithPath("name").type(JsonFieldType.STRING).description("이름"),
		fieldWithPath("motto").type(JsonFieldType.STRING).description("한 줄 다짐"),
		fieldWithPath("availablePoints").type(JsonFieldType.NUMBER).description("보유중인 포인트 양"),
		fieldWithPath("profileImageUrl").type(JsonFieldType.STRING).description("사용자 프로필 이미지의 경로"),
		fieldWithPath("profileImageName").type(JsonFieldType.STRING).description("사용자 프로필 이미지의 기존 이름"),
		fieldWithPath("roleType").type(JsonFieldType.STRING).description("사용자 권한"),
		fieldWithPath("subscriptionPlan").type(JsonFieldType.STRING).description("유료 플랜 등급"),
		fieldWithPath("activateStatus").type(JsonFieldType.STRING).description("계정 활성화 상태"),
		fieldWithPath("signUpDateTime").type(JsonFieldType.STRING).description("가입 날짜")
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			ResourceSnippetParameters.builder()
				.summary(SUMMARY)
				.description(DESCRIPTION)
				.tag(TAG)
				.responseSchema(Schema.schema("ReadMemberResponse")),
			RESPONSE_FIELDS
		);
	}
}
