package com.gomo.app.core.member.application.usecase;

import static com.gomo.app.core.member.exception.code.MemberErrorCode.*;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.member.application.port.LoginMemberPortIn;
import com.gomo.app.core.member.application.port.VerifyPasswordPortOut;
import com.gomo.app.core.member.domain.model.Email;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.model.Password;
import com.gomo.app.core.member.domain.service.MemberService;
import com.gomo.app.core.member.exception.MemberAuthenticationFailedException;
import com.gomo.app.support.logging.AuditLog;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class LoginMemberUseCase implements LoginMemberPortIn {

	private final MemberService memberService;
	private final VerifyPasswordPortOut verifyPasswordPortOut;

	@AuditLog(action = "AUTHENTICATE_MEMBER")
	@Override
	public UUID authenticate(String email, String password) {
		Member member = memberService.findByEmail(Email.of(email));
		memberService.checkActivated(member);
		ensureCorrectPassword(password, member);
		member.updateLastLoginDateTime(LocalDateTime.now());
		return member.uuid();
	}

	private void ensureCorrectPassword(String originPassword, Member member) {
		if (!verifyPasswordPortOut.matches(member.password(), Password.ofRaw(originPassword).getPassword())) {
			throw new MemberAuthenticationFailedException(AUTHENTICATION_FAILED);
		}
	}
}
