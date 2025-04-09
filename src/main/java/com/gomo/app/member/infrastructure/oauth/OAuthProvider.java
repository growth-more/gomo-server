package com.gomo.app.member.infrastructure.oauth;

import com.gomo.app.member.domain.model.OAuthUserInfo;

public interface OAuthProvider {
    OAuthUserInfo authenticate(String code);
}
