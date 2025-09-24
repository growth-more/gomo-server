package com.gomo.app.support.auth.presentation;

import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.support.auth.application.LoginMemberUseCase;
import com.gomo.app.support.auth.application.LogoutMemberUseCase;
import com.gomo.app.support.auth.application.RefreshTokenUseCase;
import com.gomo.app.support.auth.presentation.request.LoginMemberRequest;
import com.gomo.app.support.auth.presentation.response.AuthTokenResponse;
import com.gomo.app.support.auth.presentation.response.LoginMemberResponse;
import com.gomo.app.common.Presentation;
import com.gomo.app.support.auth.Auth;
import com.gomo.app.support.auth.AuthInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/members")
@Presentation
public class AuthMemberApi {

	private final LoginMemberUseCase loginMemberUseCase;
	private final LogoutMemberUseCase logoutMemberUseCase;
	private final RefreshTokenUseCase refreshTokenUseCase;

	@PostMapping("/login")
	public ResponseEntity<LoginMemberResponse> login(@RequestBody LoginMemberRequest request) {
		AuthTokenResponse tokens = loginMemberUseCase.login(request.getEmail(), request.getPassword());
		ResponseCookie cookie = createResponseCookie(tokens.getAuthToken().getRefreshToken(),
			tokens.getExpiresIn());

		return ResponseEntity.ok()
			.header(HttpHeaders.SET_COOKIE, cookie.toString())
			.body(LoginMemberResponse.of(tokens.getMemberId(), tokens.getAuthToken().getAccessToken()));
	}

	@PostMapping("/refresh")
	public ResponseEntity<LoginMemberResponse> refresh(
		@CookieValue(name = "refreshToken", required = false) String refreshToken) {
		AuthTokenResponse tokens = refreshTokenUseCase.refresh(refreshToken);
		ResponseCookie cookie = createResponseCookie(tokens.getAuthToken().getRefreshToken(),
			tokens.getExpiresIn());

		return ResponseEntity.ok()
			.header(HttpHeaders.SET_COOKIE, cookie.toString())
			.body(LoginMemberResponse.of(tokens.getMemberId(), tokens.getAuthToken().getAccessToken()));
	}

	@GetMapping("/logout")
	public ResponseEntity<Void> logout(@Auth AuthInfo authInfo) {
		logoutMemberUseCase.logout(authInfo.getMemberId());
		ResponseCookie cookie = createResponseCookie("", 0);

		return ResponseEntity.ok()
			.header(HttpHeaders.SET_COOKIE, cookie.toString())
			.build();
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
