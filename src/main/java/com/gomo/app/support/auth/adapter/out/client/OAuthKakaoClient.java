package com.gomo.app.support.auth.adapter.out.client;

import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;

import com.fasterxml.jackson.databind.JsonNode;
import com.gomo.app.core.member.domain.model.LoginProvider;
import com.gomo.app.support.auth.application.port.out.OAuthProvider;
import com.gomo.app.support.auth.domain.model.OAuthPrincipal;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component("kakao")
class OAuthKakaoClient implements OAuthProvider {

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
	public OAuthPrincipal authenticate(String code) {
		String accessToken = getAccessToken(code);
		JsonNode userInfo = getResources(accessToken);
		return OAuthPrincipal.of(LoginProvider.KAKAO, userInfo.get("email").asText(), userInfo.get("profile").get("nickname").asText());
	}

	private String getAccessToken(String code) {
		MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
		body.add("grant_type", "authorization_code");
		body.add("code", code);
		body.add("client_id", kakaoClientId);
		body.add("redirect_uri", kakaoRedirectUri);
		return restClient.post()
			.uri(kakaoTokenUri)
			.contentType(MediaType.APPLICATION_FORM_URLENCODED)
			.acceptCharset(StandardCharsets.UTF_8)
			.body(body)
			.retrieve().toEntity(JsonNode.class).getBody().get("access_token").asText();
	}

	private JsonNode getResources(String accessToken) {
		return restClient.get()
			.uri(kakaoUserInfoUri)
			.header(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8")
			.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
			.retrieve().toEntity(JsonNode.class).getBody().get("kakao_account");
	}
}
