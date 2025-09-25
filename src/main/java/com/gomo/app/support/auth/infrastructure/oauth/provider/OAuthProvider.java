package com.gomo.app.support.auth.infrastructure.oauth.provider;

import com.gomo.app.core.member.domain.model.OAuthUserInfo;

public interface OAuthProvider {
	OAuthUserInfo authenticate(String code);
}
