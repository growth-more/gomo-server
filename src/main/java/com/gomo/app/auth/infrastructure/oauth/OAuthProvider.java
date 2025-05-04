package com.gomo.app.auth.infrastructure.oauth;

import com.gomo.app.member.domain.model.OAuthUserInfo;

public interface OAuthProvider {
	OAuthUserInfo authenticate(String code);
}
