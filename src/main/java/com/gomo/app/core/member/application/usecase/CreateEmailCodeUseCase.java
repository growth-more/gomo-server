package com.gomo.app.core.member.application.usecase;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.core.member.domain.model.Email;
import com.gomo.app.core.member.domain.service.MemberService;
import com.gomo.app.support.auth.application.port.CreateAuthCodePortIn;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class CreateEmailCodeUseCase {

	private final CreateAuthCodePortIn createAuthCodePortIn;
	private final MemberService memberService;

	// TODO [2025-10-19] jhl221123 : 코드를 반환하지 않고 테스트를 처리할 수 있도록 수정해야 합니다.
	@AuditLog(action = "CREATE_SIGNUP_EMAIL_CODE")
	public String createForSignUp(String email) {
		memberService.checkEmailDuplicated(Email.of(email));
		return createAuthCodePortIn.sendToEmail(email);
	}

	@AuditLog(action = "CREATE_PW_RESET_EMAIL_CODE")
	public String createForPasswordReset(String email) {
		memberService.findByEmail(Email.of(email));
		return createAuthCodePortIn.sendToEmail(email);
	}
}
