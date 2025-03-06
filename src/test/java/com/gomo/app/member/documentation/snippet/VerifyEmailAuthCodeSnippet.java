package com.gomo.app.member.documentation.snippet;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;

import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.restdocs.restassured.RestDocumentationFilter;
import org.springframework.restdocs.snippet.Snippet;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.gomo.app.common.constant.ErrorResponseFields;

public class VerifyEmailAuthCodeSnippet {
    private static final String IDENTIFIER = "verify_email_auth_code";
    private static final String SUMMARY = "이메일 인증 코드 검증 API";
    private static final String DESCRIPTION = "이메일 인증코드를 검증합니다.";
    private static final String TAG = "Member";

    private static final ParameterDescriptor[] LIST_EMAIL_VERIFY_PARAMETERS = {
            parameterWithName("email").description("이메일"),
            parameterWithName("code").description("인증 코드")
    };

    private static final Snippet RESPONSE_FIELDS = responseFields(
            fieldWithPath("email").type(JsonFieldType.STRING).description("이메일")
    );

    public static RestDocumentationFilter create() {
        return document(
                IDENTIFIER,
                ResourceSnippetParameters.builder()
                        .summary(SUMMARY)
                        .description(DESCRIPTION)
                        .tag(TAG)
                        .queryParameters(LIST_EMAIL_VERIFY_PARAMETERS)
                        .responseSchema(Schema.schema("VerifyEmailAuthCodeResponse")),
                RESPONSE_FIELDS,
                queryParameters(LIST_EMAIL_VERIFY_PARAMETERS)
        );
    }

    public static RestDocumentationFilter createError() {
        return document(
                IDENTIFIER+"/error",
                ResourceSnippetParameters.builder()
                        .summary(SUMMARY)
                        .description(DESCRIPTION)
                        .tag(TAG)
                        .queryParameters(LIST_EMAIL_VERIFY_PARAMETERS)
                        .responseSchema(Schema.schema("ErrorResponse")),
                ErrorResponseFields.RESPONSE_FIELDS,
                queryParameters(LIST_EMAIL_VERIFY_PARAMETERS)
        );
    }
}
