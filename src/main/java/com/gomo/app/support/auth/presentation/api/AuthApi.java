package com.gomo.app.support.auth.presentation.api;

import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.Presentation;
import com.gomo.app.support.auth.application.AuthPrincipalUseCase;
import com.gomo.app.support.auth.application.DeleteRefreshTokenUseCase;
import com.gomo.app.support.auth.application.UpdateRefreshTokenUseCase;
import com.gomo.app.support.auth.presentation.request.LoginRequest;
import com.gomo.app.support.auth.presentation.response.AuthTokenResponse;
import com.gomo.app.support.auth.presentation.response.LoginResponse;
import com.gomo.app.support.auth.presentation.security.Auth;
import com.gomo.app.support.auth.presentation.security.AuthInfo;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/auth")
@Presentation
public class AuthApi {

	private final AuthPrincipalUseCase authPrincipalUseCase;
	private final UpdateRefreshTokenUseCase updateRefreshTokenUseCase;
	private final DeleteRefreshTokenUseCase deleteRefreshTokenUseCase;

	@PostMapping("/login")
	public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
		AuthTokenResponse tokens = authPrincipalUseCase.authenticate(request.getEmail(), request.getPassword());
		ResponseCookie cookie = createResponseCookie(tokens.getAuthToken().getRefreshToken(), tokens.getExpiresIn());
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
			.body(LoginResponse.of(tokens.getMemberId(), tokens.getAuthToken().getAccessToken()));
	}

	@PostMapping("/refresh")
	public ResponseEntity<LoginResponse> refresh(@CookieValue(name = "refreshToken", required = false) String refreshToken) {
		AuthTokenResponse tokens = updateRefreshTokenUseCase.update(refreshToken);
		ResponseCookie cookie = createResponseCookie(tokens.getAuthToken().getRefreshToken(), tokens.getExpiresIn());
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
			.body(LoginResponse.of(tokens.getMemberId(), tokens.getAuthToken().getAccessToken()));
	}

	@GetMapping("/logout")
	public ResponseEntity<Void> logout(@Auth AuthInfo authInfo) {
		deleteRefreshTokenUseCase.delete(authInfo.getMemberId());
		ResponseCookie cookie = createResponseCookie("", 0);
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).build();
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
