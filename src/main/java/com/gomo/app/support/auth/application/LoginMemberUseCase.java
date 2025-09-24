package com.gomo.app.support.auth.application;

import java.time.LocalDateTime;

import com.gomo.app.support.auth.domain.model.AuthToken;
import com.gomo.app.support.auth.presentation.response.AuthTokenResponse;
import com.gomo.app.common.ApplicationService;
import com.gomo.app.support.logging.AuditLog;
import com.gomo.app.core.member.domain.model.Email;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.model.Password;
import com.gomo.app.core.member.domain.service.MemberService;
import com.gomo.app.core.member.domain.service.PasswordService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class LoginMemberUseCase {

	private final MemberService memberService;
	private final PasswordService passwordService;
	private final AuthTokenGenerator authTokenGenerator;

	@AuditLog(action = "MEMBER_LOGIN")
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
