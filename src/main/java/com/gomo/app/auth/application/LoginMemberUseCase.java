package com.gomo.app.auth.application;

import java.time.LocalDateTime;

import com.gomo.app.auth.domain.model.AuthToken;
import com.gomo.app.auth.presentation.response.AuthTokenResponse;
import com.gomo.app.common.ApplicationService;
import com.gomo.app.member.domain.model.Email;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.service.MemberService;
import com.gomo.app.member.domain.service.PasswordService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class LoginMemberUseCase {

	private final MemberService memberService;
	private final PasswordService passwordService;
	private final AuthTokenIssuer authTokenIssuer;

	public AuthTokenResponse login(String email, String password) {
		Member member = memberService.findByEmail(Email.of(email));
		memberService.checkActivated(member);
		member.login(password, passwordService);
		member.updateLastLoginDateTime(LocalDateTime.now());

		AuthToken authToken = authTokenIssuer.issue(member.uuid());
		long refreshTokenExpirationTime = authTokenIssuer.getRefreshTokenExpirationTime(authToken.getRefreshToken());
		return AuthTokenResponse.of(member.uuid(), authToken, refreshTokenExpirationTime);
	}
}
