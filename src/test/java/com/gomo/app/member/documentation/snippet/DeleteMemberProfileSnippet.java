package com.gomo.app.member.documentation.snippet;

import com.epages.restdocs.apispec.ResourceSnippetParameters;
import com.epages.restdocs.apispec.Schema;
import com.gomo.app.common.constant.ErrorResponseFields;
import org.springframework.restdocs.restassured.RestDocumentationFilter;

import static com.epages.restdocs.apispec.RestAssuredRestDocumentationWrapper.document;

public class DeleteMemberProfileSnippet {

    private static final String IDENTIFIER = "delete_member_profile";
	private static final String SUMMARY = "회원 프로필이미지 삭제 API";
	private static final String DESCRIPTION = "회원 프로필 이미지를 삭제 합니다.";
	private static final String TAG = "Member";
    
    public static RestDocumentationFilter create(){
        return document(
                IDENTIFIER,
                ResourceSnippetParameters.builder()
                        .summary(SUMMARY)
                        .description(DESCRIPTION)
                        .tags(TAG)
        );
    }

    public static RestDocumentationFilter createError(){
        return document(
                IDENTIFIER+"/error",
                ResourceSnippetParameters.builder()
                        .summary(SUMMARY)
                        .description(DESCRIPTION)
                        .tags(TAG)
                        .requestSchema(Schema.schema("ErrorResponse")),
                ErrorResponseFields.RESPONSE_FIELDS
        );
    }
}
