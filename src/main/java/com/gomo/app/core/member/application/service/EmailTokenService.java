package com.gomo.app.core.member.application.service;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.core.member.application.port.in.EmailTokenIssuer;
import com.gomo.app.core.member.application.port.out.EmailCodeVerifier;
import com.gomo.app.core.member.application.port.out.EmailTokenCreator;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
class EmailTokenService implements EmailTokenIssuer {

	private final EmailCodeVerifier emailCodeVerifier;
	private final EmailTokenCreator emailTokenCreator;

	@Override
	@AuditLog(action = "VERIFIED_EMAIL_TOKEN_ISSUE")
	public String issue(String email, String emailCode) {
		emailCodeVerifier.verify(email, emailCode);
		return emailTokenCreator.create(email, 1800);
	}
}
