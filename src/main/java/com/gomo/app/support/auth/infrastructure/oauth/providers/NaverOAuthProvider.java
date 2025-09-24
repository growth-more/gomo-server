package com.gomo.app.support.auth.infrastructure.oauth.providers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.gomo.app.support.auth.infrastructure.oauth.OAuthProvider;
import com.gomo.app.core.member.domain.model.LoginProvider;
import com.gomo.app.core.member.domain.model.OAuthUserInfo;

import lombok.RequiredArgsConstructor;

@Component("naver")
@RequiredArgsConstructor
public class NaverOAuthProvider implements OAuthProvider {

	private final RestClient restClient;

	@Value("${oauth.naver.client-id}")
	private String clientId;

	@Value("${oauth.naver.client-secret}")
	private String clientSecret;

	@Value("${oauth.naver.redirect-uri}")
	private String redirectUri;

	@Value("${oauth.naver.token-uri}")
	private String tokenUri;

	@Value("${oauth.naver.user-info-uri}")
	private String userInfoUri;

	@Override
	public OAuthUserInfo authenticate(String code) {
		String accessToken = getAccessToken(code);
		JsonNode userInfo = getResources(accessToken);

		return OAuthUserInfo.builder()
			.email(userInfo.get("email").asText())
			.name(userInfo.get("name").asText())
			.provider(LoginProvider.NAVER)
			.build();
	}

	private String getAccessToken(String code) {

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "authorization_code");
		body.add("client_id", clientId);
		body.add("client_secret", clientSecret);
		body.add("code", code);

		ResponseEntity<JsonNode> response = restClient.post()
			.uri(tokenUri)
			.body(body)
			.retrieve().toEntity(JsonNode.class);
		JsonNode accessToken = response.getBody();
		return accessToken.get("access_token").asText();
	}

	private JsonNode getResources(String accessToken) {
		ResponseEntity<JsonNode> response = restClient.get()
			.uri(userInfoUri)
			.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
			.retrieve().toEntity(JsonNode.class);

		return response.getBody().get("response");
	}
}
