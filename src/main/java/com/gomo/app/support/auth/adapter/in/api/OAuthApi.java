package com.gomo.app.support.auth.adapter.in.api;

import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.support.auth.adapter.in.api.response.OAuthResponse;
import com.gomo.app.support.auth.application.port.in.OAuthLoginProcessor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/oauth/login")
@CoreApi
public class OAuthApi {

	private final OAuthLoginProcessor oAuthLoginProcessor;

	@GetMapping("/{provider}")
	public ResponseEntity<OAuthResponse> getUserInformation(@PathVariable String provider, @RequestParam String code) {
		return oAuthLoginProcessor.login(provider, code).map(oAuthTokenDto -> {
			ResponseCookie cookie = createResponseCookie(oAuthTokenDto.refreshToken(), oAuthTokenDto.expiresIn());
			return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body(OAuthResponse.from(oAuthTokenDto));
		}).orElseGet(() -> ResponseEntity.ok().body(OAuthResponse.none()));
	}

	private ResponseCookie createResponseCookie(String refreshToken, long expiresIn) {
		return ResponseCookie.from("refreshToken", refreshToken)
			.httpOnly(true)
			.secure(true)
			.path("/")
			.maxAge(Duration.ofMillis(expiresIn))
			.build();
	}
}
