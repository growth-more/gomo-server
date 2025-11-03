package com.gomo.app.core.auth.adapter.out.factory;

import java.util.Map;

import com.gomo.app.common.arch.Adapter;
import com.gomo.app.core.auth.application.port.out.OAuthProvider;
import com.gomo.app.core.auth.application.port.out.OAuthProviderReader;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Adapter
class OAuthProviderFactory implements OAuthProviderReader {

	private final Map<String, OAuthProvider> providers;

	public OAuthProvider read(String providerName) {
		OAuthProvider provider = providers.get(providerName);
		if (provider == null) {
			throw new IllegalArgumentException("Unsupported OAuth Provider: " + providerName);
		}
		return provider;
	}
}
