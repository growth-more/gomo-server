package com.gomo.app.auth.application;

import java.time.LocalDateTime;

import com.gomo.app.auth.domain.model.AuthToken;
import com.gomo.app.auth.presentation.response.AuthTokenResponse;
import com.gomo.app.common.ApplicationService;
import com.gomo.app.member.domain.model.Email;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.Password;
import com.gomo.app.member.domain.service.MemberService;
import com.gomo.app.member.domain.service.PasswordService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class LoginMemberUseCase {

	private final MemberService memberService;
	private final PasswordService passwordService;
	private final AuthTokenGenerator authTokenGenerator;

	public AuthTokenResponse login(String email, String password) {
		Member member = memberService.findByEmail(Email.of(email));
		memberService.checkActivated(member);

		Password inputPassword = Password.ofRaw(password);

		member.login(passwordService, inputPassword);
		member.updateLastLoginDateTime(LocalDateTime.now());

		AuthToken authToken = authTokenGenerator.generate(member.uuid());
		long refreshTokenExpirationTime = authTokenGenerator.getRefreshTokenExpirationTime(authToken.getRefreshToken());
		return AuthTokenResponse.of(member.uuid(), authToken, refreshTokenExpirationTime);
	}
}
