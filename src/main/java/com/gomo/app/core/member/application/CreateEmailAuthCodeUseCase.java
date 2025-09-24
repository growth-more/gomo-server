package com.gomo.app.core.member.application;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.support.logging.AuditLog;
import com.gomo.app.core.member.domain.model.Email;
import com.gomo.app.core.member.domain.repository.EmailAuthCodeRepository;
import com.gomo.app.core.member.domain.service.AuthCodeGenerator;
import com.gomo.app.core.member.domain.service.MemberService;
import com.gomo.app.core.member.infrastructure.EmailAuthSenderService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class CreateEmailAuthCodeUseCase {

	private final MemberService memberService;
	private final AuthCodeGenerator authCodeGenerator;
	private final EmailAuthSenderService emailAuthSenderService;
	private final EmailAuthCodeRepository emailAuthCodeRepository;

	@AuditLog(action = "CREATE_SIGNUP_EMAIL_CODE")
	public void createForSignUp(String email) {
		memberService.checkEmailDuplicated(Email.of(email));
		String authCode = authCodeGenerator.generate();
		emailAuthCodeRepository.save(email, authCode);
		emailAuthSenderService.sendEmailAuthCode(email, authCode);
	}

	@AuditLog(action = "CREATE_PW_RESET_EMAIL_CODE")
	public void createForPasswordReset(String email) {
		memberService.findByEmail(Email.of(email));
		String authCode = authCodeGenerator.generate();
		emailAuthCodeRepository.save(email, authCode);
		emailAuthSenderService.sendEmailAuthCode(email, authCode);
	}
}
