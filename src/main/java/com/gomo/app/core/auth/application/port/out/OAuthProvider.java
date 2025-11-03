package com.gomo.app.core.auth.application.port.out;

import com.gomo.app.core.auth.domain.model.OAuthPrincipal;

public interface OAuthProvider {

	OAuthPrincipal authenticate(String code);
}
