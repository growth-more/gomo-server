package com.gomo.app.member.infrastructure.oauth;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class OAuthProviderFactory {

    private final Map<String, OAuthProvider> providers;

    public OAuthProvider getProvider(String providerName){
        OAuthProvider provider = providers.get(providerName);
        if(provider == null){
            throw new IllegalArgumentException("Unsupported OAuth Provider: " + providerName);
        }
        return provider;
    }
}
