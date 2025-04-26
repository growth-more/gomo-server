package com.gomo.app.member.application;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.member.exception.MemberAuthenticationFailedException;
import com.gomo.app.member.exception.code.MemberErrorCode;
import com.gomo.app.member.infrastructure.EmailAuthRedisService;
import com.gomo.app.member.presentation.request.VerifyEmailAuthCodeRequest;
import com.gomo.app.member.presentation.response.VerifyEmailAuthCodeResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class VerifyEmailAuthCodeUseCase {

    private final EmailAuthRedisService emailAuthRedisService;

    public VerifyEmailAuthCodeResponse verify(VerifyEmailAuthCodeRequest request) {
		String storedCode = emailAuthRedisService.getAuthCode(request.getEmail());
        if(request.getCode() == null || !storedCode.equals(request.getCode())){
            throw new MemberAuthenticationFailedException(MemberErrorCode.AUTHENTICATION_FAILED);
        }
        emailAuthRedisService.deleteAuthCode(request.getEmail());
		return VerifyEmailAuthCodeResponse.of(request.getEmail());
	}
}
