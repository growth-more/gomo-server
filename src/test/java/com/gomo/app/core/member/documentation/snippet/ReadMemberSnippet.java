package com.gomo.app.core.member.documentation.snippet;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.restassured.RestAssuredRestDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

public class ReadMemberSnippet {
	private static final String IDENTIFIER = "member-read";

	private static final Snippet RESPONSE_FIELDS = responseFields(
		fieldWithPath("id").type(JsonFieldType.STRING).description("사용자 식별자(UUID)"),
		fieldWithPath("email").type(JsonFieldType.STRING).description("사용자 이메일"),
		fieldWithPath("handle").type(JsonFieldType.STRING).description("사용자 핸들"),
		fieldWithPath("name").type(JsonFieldType.STRING).description("사용자 이름"),
		fieldWithPath("motto").type(JsonFieldType.STRING).description("사용자의 한마디"),
		fieldWithPath("availablePoints").type(JsonFieldType.NUMBER).description("사용가능한 포인트"),
		fieldWithPath("profileImageUrl").type(JsonFieldType.STRING).description("프로필 이미지 주소"),
		fieldWithPath("profileBannerUrl").type(JsonFieldType.STRING).description("프로필 배너 주소"),
		fieldWithPath("loginProvider").type(JsonFieldType.STRING).description("회원가입 시 사용한 방식(EMAIL, GOOGLE 등)"),
		fieldWithPath("roleType").type(JsonFieldType.STRING).description("사용자 권한"),
		fieldWithPath("subscriptionPlan").type(JsonFieldType.STRING).description("유료 플랜 등급"),
		fieldWithPath("activateStatus").type(JsonFieldType.STRING).description("계정 활성화 상태"),
		fieldWithPath("signUpDateTime").type(JsonFieldType.STRING).description("가입 날짜")
	);

	public static RestDocumentationFilter create() {
		return document(
			IDENTIFIER,
			preprocessRequest(prettyPrint()),
			preprocessResponse(prettyPrint()),
			RESPONSE_FIELDS
		);
	}
}
