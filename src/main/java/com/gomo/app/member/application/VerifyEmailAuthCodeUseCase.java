package com.gomo.app.member.application;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.member.domain.service.EmailAuthCodeService;
import com.gomo.app.member.exception.MemberAuthenticationFailedException;
import com.gomo.app.member.exception.code.MemberErrorCode;
import com.gomo.app.member.presentation.request.VerifyEmailAuthCodeRequest;
import com.gomo.app.member.presentation.response.VerifyEmailAuthCodeResponse;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class VerifyEmailAuthCodeUseCase {

	private final EmailAuthCodeService emailAuthCodeService;

	public VerifyEmailAuthCodeResponse verify(VerifyEmailAuthCodeRequest request) {
		String storedCode = emailAuthCodeService.find(request.getEmail());
		if (!storedCode.equals(request.getCode()) || request.getEmail() == null) {
			throw new MemberAuthenticationFailedException(MemberErrorCode.AUTHENTICATION_FAILED);
		}
		emailAuthCodeService.remove(request.getEmail());
		return VerifyEmailAuthCodeResponse.of(request.getEmail());
	}
}
