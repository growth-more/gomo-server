package com.gomo.app.member.presentation;

import com.gomo.app.common.presentation.Presentation;
import com.gomo.app.member.application.OAuthUseCase;
import com.gomo.app.member.presentation.response.LoginMemberResponse;
import com.gomo.app.member.presentation.response.TokenResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Duration;

@RequiredArgsConstructor
@RequestMapping("/oauth/login")
@Presentation
public class OauthApi {

    private final OAuthUseCase oAuthUseCase;

    @GetMapping("/google")
    public ResponseEntity<TokenResponse> googleLogin(@RequestParam String code){
        LoginMemberResponse tokens = oAuthUseCase.login(code);
        ResponseCookie cookie = createRefreshTokenCookie(tokens.getRefreshToken(), tokens.getExpiresIn());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(TokenResponse.of(tokens.getId(), tokens.getAccessToken()));
    }

    private ResponseCookie createRefreshTokenCookie(String refreshToken, long expiry){
		return ResponseCookie.from("refreshToken", refreshToken)
				.httpOnly(true)
				.secure(true)
				.path("/members/refresh")
				.maxAge(Duration.ofSeconds(expiry))
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
