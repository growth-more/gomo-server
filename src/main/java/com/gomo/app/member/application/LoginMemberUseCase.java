package com.gomo.app.member.application;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.common.util.JwtUtil;
import com.gomo.app.member.domain.model.ActivateStatus;
import com.gomo.app.member.domain.model.Email;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.domain.service.PasswordService;
import com.gomo.app.member.exception.MemberErrorCode;
import com.gomo.app.member.exception.MemberNotFoundException;
import com.gomo.app.member.exception.MemberPolicyViolationException;
import com.gomo.app.member.infrastructure.JwtSessionRedisService;
import com.gomo.app.member.presentation.request.LoginMemberRequest;
import com.gomo.app.member.presentation.response.CreateMemberResponse;
import com.gomo.app.member.presentation.response.LoginMemberResponse;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

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
				.orElseThrow(() -> new MemberNotFoundException(MemberErrorCode.NOT_FOUND, "check email or password"));

		switch(member.getActivateStatus()){
			case DELETED:
				throw new MemberPolicyViolationException(MemberErrorCode.MEMBER_DELETED, "member info has been deleted. check email or password");
			case BLOCKED:
				throw new MemberPolicyViolationException(MemberErrorCode.MEMBER_BANNED, "member info has been blocked. check email or password");
		}

		// 비밀번호 검증
		passwordService.matches(request.getPassword(), member.getPassword());

		// token 생성
		String accessToken = jwtUtil.generateAccessToken(member.getId());
		String refreshToken = jwtUtil.generateRefreshToken(member.getId());
		long refreshTokenExptime = jwtUtil.extractExpirationTime(refreshToken);

		jwtSessionRedisService.setRefreshToken(member.getId(), refreshToken);

		member.updateLastLoginDateTime(LocalDateTime.now());

		return LoginMemberResponse.of(member.getId(), accessToken, refreshToken, refreshTokenExptime);
	}
}
