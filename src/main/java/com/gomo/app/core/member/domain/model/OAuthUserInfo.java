package com.gomo.app.core.member.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OAuthUserInfo {
    private LoginProvider provider;
    private String email;
    private String name;
}
