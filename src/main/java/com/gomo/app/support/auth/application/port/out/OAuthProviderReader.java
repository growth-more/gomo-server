package com.gomo.app.support.auth.application.port.out;

public interface OAuthProviderReader {

	OAuthProvider read(String providerName);
}
