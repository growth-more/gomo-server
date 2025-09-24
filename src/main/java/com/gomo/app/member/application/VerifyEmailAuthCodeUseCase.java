package com.gomo.app.member.application;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.logging.AuditLog;
import com.gomo.app.member.application.port.dto.VerifyEmailAuthCodeDto;
import com.gomo.app.member.domain.service.EmailAuthCodeService;
import com.gomo.app.member.exception.MemberAuthenticationFailedException;
import com.gomo.app.member.exception.code.MemberErrorCode;

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
