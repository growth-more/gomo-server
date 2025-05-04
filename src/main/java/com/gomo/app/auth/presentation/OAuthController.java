package com.gomo.app.auth.presentation;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gomo.app.auth.application.OAuthUseCase;
import com.gomo.app.auth.presentation.response.AuthTokenResponse;
import com.gomo.app.auth.presentation.response.LoginMemberResponse;
import com.gomo.app.common.Presentation;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/oauth/login")
@Presentation
public class OAuthController {

	private final OAuthUseCase oauthUseCase;
	private final RefreshTokenCookieProvider refreshTokenCookieProvider;

	@GetMapping("/{provider}")
	public ResponseEntity<LoginMemberResponse> oauthLogin(@PathVariable String provider, @RequestParam String code) {
		AuthTokenResponse tokens = oauthUseCase.login(provider, code);
		ResponseCookie cookie = refreshTokenCookieProvider.create(tokens.getAuthToken().getRefreshToken(),
			tokens.getExpiresIn());

		return ResponseEntity.ok()
			.header(HttpHeaders.SET_COOKIE, cookie.toString())
			.body(LoginMemberResponse.of(tokens.getMemberId(), tokens.getAuthToken().getAccessToken()));
	}
}
