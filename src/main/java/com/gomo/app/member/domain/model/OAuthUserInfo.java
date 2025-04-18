package com.gomo.app.member.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuthUserInfo {
    private String providerId;
    private String email;
    private String name;
}
