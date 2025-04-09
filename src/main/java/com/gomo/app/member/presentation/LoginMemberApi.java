package com.gomo.app.member.presentation;

import com.gomo.app.common.authentication.Auth;
import com.gomo.app.common.authentication.AuthInfo;
import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.member.application.LoginMemberUseCase;
import com.gomo.app.member.application.LogoutMemberUseCase;
import com.gomo.app.member.application.RefreshAccessTokenUseCase;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.presentation.request.LoginMemberRequest;
import com.gomo.app.member.presentation.response.LoginMemberResponse;
import com.gomo.app.member.presentation.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RequiredArgsConstructor
@RequestMapping("/members")
@Presentation
public class LoginMemberApi {

	private final LoginMemberUseCase loginMemberUseCase;
	private final LogoutMemberUseCase logoutMemberUseCase;
	private final RefreshAccessTokenUseCase refreshAccessTokenUseCase;

	@PostMapping("/login")
	public ResponseEntity<TokenResponse> login(@RequestBody LoginMemberRequest loginMemberRequest) {
		LoginMemberResponse tokens = loginMemberUseCase.login(loginMemberRequest);
		ResponseCookie cookie = createRefreshTokenCookie(tokens.getRefreshToken(), tokens.getExpiresIn());

		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body(TokenResponse.of(tokens.getId(), tokens.getAccessToken()));
	}

	@PostMapping("/refresh")
	public ResponseEntity<TokenResponse> refresh(@CookieValue(name="refreshToken", required=false) String refreshToken){
		LoginMemberResponse tokens = refreshAccessTokenUseCase.refresh(refreshToken);
		ResponseCookie cookie = createRefreshTokenCookie(tokens.getRefreshToken(), tokens.getExpiresIn());

		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, cookie.toString())
				.body(TokenResponse.of(tokens.getId(), tokens.getAccessToken()));
	}

	@GetMapping("/logout")
	public ResponseEntity<Void> logout(@Auth AuthInfo authInfo){
		logoutMemberUseCase.logout(MemberId.of(authInfo.getMemberId()));
		ResponseCookie cookie = deleteRefreshTokenCookie();

		return ResponseEntity.ok()
				.header(HttpHeaders.SET_COOKIE, cookie.toString())
				.build();
	}

	private ResponseCookie createRefreshTokenCookie(String refreshToken, long expiry){
		return ResponseCookie.from("refreshToken", refreshToken)
				.httpOnly(true)
				.secure(true)
				.path("/members/refresh")
				.maxAge(Duration.ofMillis(expiry))
				.build();
	}

	private ResponseCookie deleteRefreshTokenCookie(){
		return ResponseCookie.from("refreshToken", "")
				.httpOnly(true)
				.secure(true)
				.path("/members/refresh")
				.maxAge(0)
				.build();
	}
}
