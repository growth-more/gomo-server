package com.gomo.app.core.member.application.usecase;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.member.domain.model.Email;
import com.gomo.app.core.member.domain.service.MemberService;
import com.gomo.app.support.auth.application.port.CreateAuthCodePortIn;
import com.gomo.app.support.logging.AuditLog;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class CreateEmailCodeUseCase {

	private final MemberService memberService;
	private final CreateAuthCodePortIn createAuthCodePortIn;

	@AuditLog(action = "CREATE_SIGNUP_EMAIL_CODE")
	public void createForSignUp(String email) {
		memberService.checkEmailDuplicated(Email.of(email));
		createAuthCodePortIn.sendToEmail(email);
	}

	@AuditLog(action = "CREATE_PW_RESET_EMAIL_CODE")
	public void createForPasswordReset(String email) {
		memberService.findByEmail(Email.of(email));
		createAuthCodePortIn.sendToEmail(email);
	}
}
