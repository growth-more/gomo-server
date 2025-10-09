package com.gomo.app.test;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.snippet.Snippet;

public interface ErrorResponseFields {

	Snippet RESPONSE_FIELDS = responseFields(
		fieldWithPath("timestamp").type(JsonFieldType.STRING).description("에러 발생 시간"),
		fieldWithPath("httpStatus").type(JsonFieldType.NUMBER).description("http 응답 상태 코드"),
		fieldWithPath("code").type(JsonFieldType.STRING).description("에러 코드"),
		fieldWithPath("message").type(JsonFieldType.STRING).description("에러 메시지"),
		fieldWithPath("path").type(JsonFieldType.STRING).description("요청 url")
	);
}
