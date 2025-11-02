package com.gomo.app.support.auth.adapter.out.client;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.gomo.app.core.member.domain.model.LoginProvider;
import com.gomo.app.support.auth.application.port.out.OAuthProvider;
import com.gomo.app.support.auth.domain.model.OAuthPrincipal;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component("google")
class OAuthGoogleClient implements OAuthProvider {

	private final RestClient restClient;

	@Value("${oauth.google.client-id}")
	private String clientId;

	@Value("${oauth.google.client-secret}")
	private String clientSecret;

	@Value("${oauth.google.redirect-uri}")
	private String redirectUri;

	@Value("${oauth.google.token-uri}")
	private String tokenUri;

	@Value("${oauth.google.user-info-uri}")
	private String userInfoUri;

	@Override
	public OAuthPrincipal authenticate(String code) {
		String accessToken = getAccessToken(code);
		JsonNode userInfo = getUserResource(accessToken);
		return OAuthPrincipal.of(LoginProvider.GOOGLE, userInfo.get("email").asText(), userInfo.get("name").asText());
	}

	private String getAccessToken(String code) {
		return restClient.post()
			.uri(tokenUri)
			.body(Map.of(
				"code", code,
				"client_id", clientId,
				"client_secret", clientSecret,
				"redirect_uri", redirectUri,
				"grant_type", "authorization_code"
			)).retrieve()
			.toEntity(JsonNode.class).getBody().get("access_token").asText();
	}

	private JsonNode getUserResource(String accessToken) {
		return restClient.get()
			.uri(userInfoUri)
			.header("Authorization", "Bearer " + accessToken)
			.retrieve()
			.toEntity(JsonNode.class).getBody();
	}
}
