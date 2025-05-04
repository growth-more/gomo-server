package com.gomo.app.auth.infrastructure.oauth.providers;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.gomo.app.auth.infrastructure.oauth.OAuthProvider;
import com.gomo.app.member.domain.model.OAuthUserInfo;

import lombok.RequiredArgsConstructor;

@Component("kakao")
@RequiredArgsConstructor
public class KakaoOAuthProvider implements OAuthProvider {

	private final RestClient restClient;

	@Value("${oauth.kakao.client-id}")
	private String kakaoClientId;

	@Value("${oauth.kakao.redirect-uri}")
	private String kakaoRedirectUri;

	@Value("${oauth.kakao.token-uri}")
	private String kakaoTokenUri;

	@Value("${oauth.kakao.user-info-uri}")
	private String kakaoUserInfoUri;

	@Override
	public OAuthUserInfo authenticate(String code) {
		String accessToken = getAccessToken(code);
		JsonNode userInfo = getResources(accessToken);

		return OAuthUserInfo.builder()
			.email(userInfo.get("email").asText())
			.name(userInfo.get("profile").get("nickname").asText())
			.providerId("kakao")
			.build();
	}

	private String getAccessToken(String code) {

		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "authorization_code");
		body.add("code", code);
		body.add("client_id", kakaoClientId);
		body.add("redirect_uri", kakaoRedirectUri);

		ResponseEntity<JsonNode> resposne = restClient.post()
			.uri(kakaoTokenUri)
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.acceptCharset(StandardCharsets.UTF_8)
			.body(body)
			.retrieve().toEntity(JsonNode.class);
		JsonNode accessToken = resposne.getBody();
		return accessToken.get("access_token").asText();
	}

	private JsonNode getResources(String accessToken) {
		ResponseEntity<JsonNode> response = restClient.get()
			.uri(kakaoUserInfoUri)
			.header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
			.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
			.retrieve().toEntity(JsonNode.class);
		return response.getBody().get("kakao_account");
	}
}
