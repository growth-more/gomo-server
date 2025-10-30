package com.gomo.app.core.member.application.service;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.core.member.application.port.in.EmailCodeIssuer;
import com.gomo.app.core.member.application.port.out.EmailCodeSender;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
class EmailCodeService implements EmailCodeIssuer {

	private final EmailService emailService;
	private final MemberService memberService;
	private final EmailCodeSender emailCodeSender;

	@Override
	@AuditLog(action = "SIGNUP_EMAIL_CODE_SEND")
	public void issueForSignUp(String email) {
		emailService.validateDuplicated(email);
		emailCodeSender.send(email);
	}

	@Override
	@AuditLog(action = "PW_RESET_EMAIL_CODE_SEND")
	public void issueForPasswordReset(String email) {
		memberService.findByEmail(email);
		emailCodeSender.send(email);
	}
}
