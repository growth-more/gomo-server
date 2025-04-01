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

public class OAuthSnippet {

    private static final String IDENTIFIER = "oauth_login";
	private static final String SUMMARY = "OAUTH LOGIN API";
	private static final String DESCRIPTION = "OAuth 로그인 API 입니다.";
	private static final String TAG = "Member";

    private static final ParameterDescriptor[] LIST_OAUTH_PARAMETERS = {
            parameterWithName("code").description("Google에서 발급한 Code")
    };

    private static final Snippet RESPONSE_FIELDS = responseFields(
            fieldWithPath("id").type(JsonFieldType.STRING).description("Member ID"),
            fieldWithPath("token").type(JsonFieldType.STRING).description("Access Token")
    );

    public static RestDocumentationFilter create(){
        return document(
                IDENTIFIER,
                ResourceSnippetParameters.builder()
                        .summary(SUMMARY)
                        .description(DESCRIPTION)
                        .tags(TAG)
                        .queryParameters(LIST_OAUTH_PARAMETERS),
                queryParameters(LIST_OAUTH_PARAMETERS),
                RESPONSE_FIELDS
        );
    }

    public static RestDocumentationFilter createError(){
        return document(
                IDENTIFIER,
                ResourceSnippetParameters.builder()
                        .summary(SUMMARY)
                        .description(DESCRIPTION)
                        .tags(TAG)
                        .queryParameters(LIST_OAUTH_PARAMETERS)
                        .requestSchema(Schema.schema("ErrorResponse")),
                queryParameters(LIST_OAUTH_PARAMETERS),
                ErrorResponseFields.RESPONSE_FIELDS
        );
    }

}
