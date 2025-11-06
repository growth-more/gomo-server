package com.gomo.app.core.auth.adapter.in.api;

import static org.springframework.http.HttpStatus.*;

import java.time.Duration;
import java.util.UUID;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.gomo.app.common.arch.CoreApi;
import com.gomo.app.common.session.Session;
import com.gomo.app.common.session.SessionInfo;
import com.gomo.app.core.auth.adapter.in.api.request.CreatePrincipalRequest;
import com.gomo.app.core.auth.adapter.in.api.request.LoginRequest;
import com.gomo.app.core.auth.adapter.in.api.request.ResetPasswordRequest;
import com.gomo.app.core.auth.adapter.in.api.response.AccessTokenResponse;
import com.gomo.app.core.auth.adapter.in.api.response.CreatePrincipalResponse;
import com.gomo.app.core.auth.application.port.dto.AuthTokenDto;
import com.gomo.app.core.auth.application.port.in.LoginProcessor;
import com.gomo.app.core.auth.application.port.in.PasswordResetProcessor;
import com.gomo.app.core.auth.application.port.in.RefreshTokenDeleter;
import com.gomo.app.core.auth.application.port.in.RefreshTokenUpdater;
import com.gomo.app.core.auth.application.port.in.SignupProcessor;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/auth")
@CoreApi
public class AuthApi {

	private final SignupProcessor signupProcessor;
	private final PasswordResetProcessor passwordResetProcessor;
	private final LoginProcessor loginProcessor;
	private final RefreshTokenUpdater refreshTokenUpdater;
	private final RefreshTokenDeleter refreshTokenDeleter;

	@PostMapping("/signup")
	public ResponseEntity<CreatePrincipalResponse> signup(@RequestBody CreatePrincipalRequest request) {
		UUID principalId = signupProcessor.signup(request.toCommand());
		return ResponseEntity.status(HttpStatus.CREATED).body(CreatePrincipalResponse.of(principalId));
	}

	@PutMapping("/passwords/reset")
	public ResponseEntity<Void> reset(@RequestBody ResetPasswordRequest request) {
		passwordResetProcessor.reset(request.getEmail(), request.getNewPassword(), request.getTemporaryToken());
		return ResponseEntity.noContent().build();
	}

	@PostMapping("/login")
	public ResponseEntity<AccessTokenResponse> login(@RequestBody LoginRequest request) {
		AuthTokenDto authTokenDto = loginProcessor.login(request.getEmail(), request.getPassword());
		ResponseCookie cookie = createResponseCookie(authTokenDto.refreshToken(), authTokenDto.expiresIn());
		AccessTokenResponse body = AccessTokenResponse.of(authTokenDto.principalId(), authTokenDto.accessToken());
		return ResponseEntity.status(OK).header(HttpHeaders.SET_COOKIE, cookie.toString()).body(body);
	}

	@PostMapping("/refresh")
	public ResponseEntity<AccessTokenResponse> refresh(@CookieValue(name = "refreshToken", required = false) String refreshToken) {
		AuthTokenDto authTokenDto = refreshTokenUpdater.update(refreshToken);
		ResponseCookie cookie = createResponseCookie(authTokenDto.refreshToken(), authTokenDto.expiresIn());
		return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
			.body(AccessTokenResponse.of(authTokenDto.principalId(), authTokenDto.accessToken()));
	}

	@GetMapping("/logout")
	public ResponseEntity<Void> logout(@Session SessionInfo sessionInfo) {
		refreshTokenDeleter.delete(sessionInfo.getPrincipalId());
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
