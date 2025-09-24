package com.gomo.app.core.member.application;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.support.logging.AuditLog;
import com.gomo.app.core.member.application.port.dto.VerifyEmailAuthCodeDto;
import com.gomo.app.core.member.domain.service.EmailAuthCodeService;
import com.gomo.app.core.member.exception.MemberAuthenticationFailedException;
import com.gomo.app.core.member.exception.code.MemberErrorCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class VerifyEmailAuthCodeUseCase {

	private final EmailAuthCodeService emailAuthCodeService;

	@AuditLog(action = "VERIFY_EMAIL_CODE")
	public VerifyEmailAuthCodeDto verify(String email, String authCode) {
		ensureCorrectAuthCode(email, authCode);
		emailAuthCodeService.remove(email);
		return VerifyEmailAuthCodeDto.of(email);
	}

	private void ensureCorrectAuthCode(String email, String authCode) {
		String storedCode = emailAuthCodeService.find(email);
		if (!storedCode.equals(authCode) || email == null) {
			throw new MemberAuthenticationFailedException(MemberErrorCode.AUTHENTICATION_FAILED);
		}
	}
}
