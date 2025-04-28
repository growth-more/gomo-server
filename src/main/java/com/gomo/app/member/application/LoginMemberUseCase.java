package com.gomo.app.member.application;

import java.time.LocalDateTime;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.common.util.JwtUtil;
import com.gomo.app.member.domain.model.Email;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.service.MemberService;
import com.gomo.app.member.domain.service.PasswordService;
import com.gomo.app.member.exception.ActivateStatusException;
import com.gomo.app.member.exception.code.ActivateStatusErrorCode;
import com.gomo.app.member.infrastructure.JwtSessionRedisService;
import com.gomo.app.member.presentation.response.LoginMemberResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class LoginMemberUseCase {

	private final MemberService memberService;
	private final PasswordService passwordService;
	private final JwtSessionRedisService jwtSessionRedisService;
	private final JwtUtil jwtUtil;

	@Transactional
	public LoginMemberResponse login(String email, String password) {
		Member member = memberService.findByEmail(Email.of(email));
		ensureActiveMember(member);
		member.login(password, passwordService);
		member.updateLastLoginDateTime(LocalDateTime.now());

		String accessToken = jwtUtil.generateAccessToken(member.uuid());
		String refreshToken = jwtUtil.generateRefreshToken(member.uuid());
		jwtSessionRedisService.setRefreshToken(member.uuid(), refreshToken);
		long refreshTokenExpirationTime = jwtUtil.extractExpirationTime(refreshToken);

		return LoginMemberResponse.of(member.uuid(), accessToken, refreshToken, refreshTokenExpirationTime);
	}

	private void ensureActiveMember(Member member) {
		switch(member.getActivateStatus()){
			case DELETED -> throw new ActivateStatusException(ActivateStatusErrorCode.DELETED);
			case BLOCKED -> throw new ActivateStatusException(ActivateStatusErrorCode.BLOCKED);
		}
	}
}
