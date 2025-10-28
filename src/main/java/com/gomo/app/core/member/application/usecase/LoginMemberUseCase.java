package com.gomo.app.core.member.application.usecase;

import static com.gomo.app.core.member.exception.code.MemberErrorCode.*;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.common.security.encoder.application.port.VerifyPasswordPortIn;
import com.gomo.app.core.member.application.port.LoginMemberPortIn;
import com.gomo.app.core.member.domain.model.Email;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.model.Password;
import com.gomo.app.core.member.domain.service.MemberService;
import com.gomo.app.core.member.exception.MemberAuthenticationFailedException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
class LoginMemberUseCase implements LoginMemberPortIn {

	private final VerifyPasswordPortIn verifyPasswordPortIn;
	private final MemberService memberService;

	@AuditLog(action = "AUTHENTICATE_MEMBER")
	@Override
	public UUID authenticate(String email, String password) {
		Member member = memberService.findByEmail(Email.of(email));
		ensureCorrectPassword(member, password);
		member.validateActive();
		member.validateLoginProviderIsEmail();
		member.updateLastLoginDateTime(LocalDateTime.now());
		return member.getId();
	}

	private void ensureCorrectPassword(Member member, String inputPassword) {
		if (!verifyPasswordPortIn.matches(Password.ofRaw(inputPassword).getPassword(), member.password())) {
			throw new MemberAuthenticationFailedException(AUTHENTICATION_FAILED);
		}
	}
}
