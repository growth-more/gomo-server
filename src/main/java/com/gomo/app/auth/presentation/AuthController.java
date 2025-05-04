package com.gomo.app.auth.presentation;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.auth.application.LoginMemberUseCase;
import com.gomo.app.auth.application.LogoutMemberUseCase;
import com.gomo.app.auth.application.RefreshTokenUseCase;
import com.gomo.app.auth.presentation.request.LoginMemberRequest;
import com.gomo.app.auth.presentation.response.AuthTokenResponse;
import com.gomo.app.auth.presentation.response.LoginMemberResponse;
import com.gomo.app.common.Presentation;
import com.gomo.app.common.authentication.Auth;
import com.gomo.app.common.authentication.AuthInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members")
@Presentation
public class AuthController {

	private final LoginMemberUseCase loginMemberUseCase;
	private final LogoutMemberUseCase logoutMemberUseCase;
	private final RefreshTokenUseCase refreshTokenUseCase;
	private final RefreshTokenCookieProvider refreshTokenCookieProvider;

	@PostMapping("/login")
	public ResponseEntity<LoginMemberResponse> login(@RequestBody LoginMemberRequest request) {
		AuthTokenResponse tokens = loginMemberUseCase.login(request.getEmail(), request.getPassword());
		ResponseCookie cookie = refreshTokenCookieProvider.create(tokens.getAuthToken().getRefreshToken(),
			tokens.getExpiresIn());

		return ResponseEntity.ok()
			.header(HttpHeaders.SET_COOKIE, cookie.toString())
			.body(LoginMemberResponse.of(tokens.getMemberId(), tokens.getAuthToken().getAccessToken()));
	}

	@PostMapping("/refresh")
	public ResponseEntity<LoginMemberResponse> refresh(
		@CookieValue(name = "refreshToken", required = false) String refreshToken) {
		AuthTokenResponse tokens = refreshTokenUseCase.refresh(refreshToken);
		ResponseCookie cookie = refreshTokenCookieProvider.create(tokens.getAuthToken().getRefreshToken(),
			tokens.getExpiresIn());

		return ResponseEntity.ok()
			.header(HttpHeaders.SET_COOKIE, cookie.toString())
			.body(LoginMemberResponse.of(tokens.getMemberId(), tokens.getAuthToken().getAccessToken()));
	}

	@GetMapping("/logout")
	public ResponseEntity<Void> logout(@Auth AuthInfo authInfo) {
		logoutMemberUseCase.logout(authInfo.getMemberId());
		ResponseCookie cookie = refreshTokenCookieProvider.delete();

		return ResponseEntity.ok()
			.header(HttpHeaders.SET_COOKIE, cookie.toString())
			.build();
	}
}
