package com.gomo.app.core.auth.application.port.out;

public interface OAuthProviderReader {

	OAuthProvider read(String providerName);
}
