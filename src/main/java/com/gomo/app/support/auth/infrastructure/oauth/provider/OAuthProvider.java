package com.gomo.app.support.auth.infrastructure.oauth.provider;

import com.gomo.app.support.auth.domain.model.OAuthPrincipal;

public interface OAuthProvider {
	
	OAuthPrincipal authenticate(String code);
}
