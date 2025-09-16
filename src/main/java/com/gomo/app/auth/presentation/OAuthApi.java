package com.gomo.app.auth.presentation;

import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gomo.app.auth.application.OAuthUseCase;
import com.gomo.app.auth.presentation.response.OAuthResponse;
import com.gomo.app.auth.presentation.response.OAuthTokenResponse;
import com.gomo.app.common.Presentation;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/oauth/login")
@Presentation
public class OAuthApi {

	private final OAuthUseCase oauthUseCase;

	@GetMapping("/{provider}")
	public ResponseEntity<OAuthResponse> getUserInformation(@PathVariable String provider, @RequestParam String code) {
		OAuthTokenResponse tokens = oauthUseCase.getUserInformation(provider, code);

		ResponseCookie cookie;

		if (tokens.getAuthToken() != null){
			cookie = createResponseCookie(tokens.getAuthToken().getRefreshToken(),
				tokens.getExpiresIn());
			return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body(OAuthResponse.of(tokens.getAuthToken().getAccessToken(), tokens.getUserInfo()));
		} else {
			return ResponseEntity.ok()
				.body(OAuthResponse.of(null, tokens.getUserInfo()));
		}
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
