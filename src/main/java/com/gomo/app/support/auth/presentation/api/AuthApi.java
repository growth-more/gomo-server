package com.gomo.app.support.auth.presentation.api;

import static org.springframework.http.HttpStatus.*;

import java.time.Duration;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.support.auth.application.port.DeleteAuthTokenPortIn;
import com.gomo.app.support.auth.application.port.dto.AuthTokenDto;
import com.gomo.app.support.auth.application.usecase.AuthenticateUseCase;
import com.gomo.app.support.auth.application.usecase.UpdateRefreshTokenUseCase;
import com.gomo.app.support.auth.presentation.request.LoginRequest;
import com.gomo.app.support.auth.presentation.response.AccessTokenResponse;
import com.gomo.app.support.auth.presentation.security.Auth;
import com.gomo.app.support.auth.presentation.security.AuthInfo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/auth")
@CoreApi
public class AuthApi {

	private final AuthenticateUseCase authenticateUseCase;
	private final UpdateRefreshTokenUseCase updateRefreshTokenUseCase;
	private final DeleteAuthTokenPortIn deleteAuthTokenPortIn;

	@PostMapping("/login")
	public ResponseEntity<AccessTokenResponse> login(@RequestBody LoginRequest request) {
		AuthTokenDto authTokenDto = authenticateUseCase.authenticate(request.getEmail(), request.getPassword());
		ResponseCookie cookie = createResponseCookie(authTokenDto.refreshToken(), authTokenDto.expiresIn());
		AccessTokenResponse body = AccessTokenResponse.of(authTokenDto.principalId(), authTokenDto.accessToken());
		return ResponseEntity.status(OK).header(HttpHeaders.SET_COOKIE, cookie.toString()).body(body);
	}

	@PostMapping("/refresh")
	public ResponseEntity<AccessTokenResponse> refresh(@CookieValue(name = "refreshToken", required = false) String refreshToken) {
		AuthTokenDto authTokenDto = updateRefreshTokenUseCase.update(refreshToken);
		ResponseCookie cookie = createResponseCookie(authTokenDto.refreshToken(), authTokenDto.expiresIn());
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
			.body(AccessTokenResponse.of(authTokenDto.principalId(), authTokenDto.accessToken()));
	}

	@GetMapping("/logout")
	public ResponseEntity<Void> logout(@Auth AuthInfo authInfo) {
		deleteAuthTokenPortIn.deleteRefreshToken(authInfo.getPrincipalId());
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
