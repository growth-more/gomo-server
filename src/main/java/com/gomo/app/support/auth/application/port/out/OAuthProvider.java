package com.gomo.app.support.auth.application.port.out;

import com.gomo.app.support.auth.domain.model.OAuthPrincipal;

public interface OAuthProvider {

	OAuthPrincipal authenticate(String code);
}
