package com.gomo.app.member.application;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.member.exception.MemberAuthenticationFailedException;
import com.gomo.app.member.exception.code.MemberErrorCode;
import com.gomo.app.member.infrastructure.repository.EmailAuthRedisRepository;
import com.gomo.app.member.presentation.request.VerifyEmailAuthCodeRequest;
import com.gomo.app.member.presentation.response.VerifyEmailAuthCodeResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class VerifyEmailAuthCodeUseCase {

	private final EmailAuthRedisRepository emailAuthRedisRepository;

	public VerifyEmailAuthCodeResponse verify(VerifyEmailAuthCodeRequest request) {
		String storedCode = emailAuthRedisRepository.getAuthCode(request.getEmail());
		if (request.getCode() == null || !storedCode.equals(request.getCode())) {
			throw new MemberAuthenticationFailedException(MemberErrorCode.AUTHENTICATION_FAILED);
		}
		emailAuthRedisRepository.deleteAuthCode(request.getEmail());
		return VerifyEmailAuthCodeResponse.of(request.getEmail());
	}
}
