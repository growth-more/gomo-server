package com.gomo.app.core.member.application.usecase;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.security.jwt.application.port.GenerateJwtPortIn;
import com.gomo.app.support.auth.application.port.VerifyAuthCodePortIn;
import com.gomo.app.support.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class VerifyEmailCodeUseCase {

	private final VerifyAuthCodePortIn verifyAuthCodePortIn;
	private final GenerateJwtPortIn generateJwtPortIn;

	@AuditLog(action = "VERIFY_EMAIL_CODE")
	public String verify(String email, String authCode) {
		verifyAuthCodePortIn.verify(email, authCode);
		return generateJwtPortIn.generateTemporaryToken(email, 300);
	}
}
