package com.gomo.app.auth.infrastructure.oauth.providers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.gomo.app.auth.infrastructure.oauth.OAuthProvider;
import com.gomo.app.member.domain.model.OAuthUserInfo;

import lombok.RequiredArgsConstructor;

@Component("google")
@RequiredArgsConstructor
public class GoogleOAuthProvider implements OAuthProvider {
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
	public OAuthUserInfo authenticate(String code) {
		String accessToken = getAccessToken(code);
		JsonNode userInfo = getUserResource(accessToken);

		return OAuthUserInfo.builder()
			.email(userInfo.get("email").asText())
			.name(userInfo.get("name").asText())
			.providerId("google")
			.build();
	}

	private String getAccessToken(String code) {
		ResponseEntity<JsonNode> response = restClient.post()
			.uri(tokenUri)
			.body(Map.of(
				"code", code,
				"client_id", clientId,
				"client_secret", clientSecret,
				"redirect_uri", redirectUri,
				"grant_type", "authorization_code"
			)).retrieve()
			.toEntity(JsonNode.class);
		JsonNode accessToken = response.getBody();
		return accessToken.get("access_token").asText();
	}

	private JsonNode getUserResource(String accessToken) {
		ResponseEntity<JsonNode> response = restClient.get()
			.uri(userInfoUri)
			.header("Authorization", "Bearer " + accessToken)
			.retrieve()
			.toEntity(JsonNode.class);
		return response.getBody();
	}
}
