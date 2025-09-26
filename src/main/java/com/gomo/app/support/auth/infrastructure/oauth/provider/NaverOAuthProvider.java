package com.gomo.app.support.auth.infrastructure.oauth.provider;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.gomo.app.core.member.domain.model.LoginProvider;
import com.gomo.app.support.auth.domain.model.OAuthPrincipal;

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
	public OAuthPrincipal authenticate(String code) {
		String accessToken = getAccessToken(code);
		JsonNode userInfo = getResources(accessToken);
		return OAuthPrincipal.of(LoginProvider.NAVER, userInfo.get("email").asText(), userInfo.get("name").asText());
	}

	private String getAccessToken(String code) {
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "authorization_code");
		body.add("client_id", clientId);
		body.add("client_secret", clientSecret);
		body.add("code", code);
		return restClient.post()
			.uri(tokenUri)
			.body(body)
			.retrieve().toEntity(JsonNode.class).getBody().get("access_token").asText();
	}

	private JsonNode getResources(String accessToken) {
		return restClient.get()
			.uri(userInfoUri)
			.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
			.retrieve().toEntity(JsonNode.class).getBody().get("response");
	}
}
