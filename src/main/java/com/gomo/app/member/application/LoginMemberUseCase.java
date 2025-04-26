package com.gomo.app.member.application;

import java.time.LocalDateTime;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.common.util.JwtUtil;
import com.gomo.app.member.domain.model.Email;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.domain.service.PasswordService;
import com.gomo.app.member.exception.ActivateStatusException;
import com.gomo.app.member.exception.MemberNotFoundException;
import com.gomo.app.member.exception.code.ActivateStatusErrorCode;
import com.gomo.app.member.exception.code.MemberErrorCode;
import com.gomo.app.member.infrastructure.JwtSessionRedisService;
import com.gomo.app.member.presentation.request.LoginMemberRequest;
import com.gomo.app.member.presentation.response.LoginMemberResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class LoginMemberUseCase {

	private final MemberRepository memberRepository;
	private final PasswordService passwordService;
	private final JwtUtil jwtUtil;
	private final JwtSessionRedisService jwtSessionRedisService;

	@Transactional
	public LoginMemberResponse login(LoginMemberRequest request) {
		Member member = memberRepository.findByEmail(Email.of(request.getEmail()))
				.orElseThrow(() -> new MemberNotFoundException(MemberErrorCode.NOT_FOUND));

		switch(member.getActivateStatus()){
			case DELETED -> throw new ActivateStatusException(ActivateStatusErrorCode.DELETED);
			case BLOCKED -> throw new ActivateStatusException(ActivateStatusErrorCode.BLOCKED);
		}

		member.login(request.getPassword(), passwordService);

		String accessToken = jwtUtil.generateAccessToken(member.getId());
		String refreshToken = jwtUtil.generateRefreshToken(member.getId());
		long refreshTokenExpirationTime = jwtUtil.extractExpirationTime(refreshToken);

		jwtSessionRedisService.setRefreshToken(member.getId(), refreshToken);

		member.updateLastLoginDateTime(LocalDateTime.now());

		return LoginMemberResponse.of(member.getId(), accessToken, refreshToken, refreshTokenExpirationTime);
	}
}
