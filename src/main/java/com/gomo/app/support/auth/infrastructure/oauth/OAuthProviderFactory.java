package com.gomo.app.support.auth.infrastructure.oauth;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.gomo.app.support.auth.infrastructure.oauth.provider.OAuthProvider;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class OAuthProviderFactory {

	private final Map<String, OAuthProvider> providers;

	public OAuthProvider getProvider(String providerName) {
		OAuthProvider provider = providers.get(providerName);
		if (provider == null) {
			throw new IllegalArgumentException("Unsupported OAuth Provider: " + providerName);
		}
		return provider;
	}
}
