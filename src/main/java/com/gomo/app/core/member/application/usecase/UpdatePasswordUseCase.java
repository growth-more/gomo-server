package com.gomo.app.core.member.application.usecase;

import static com.gomo.app.core.member.exception.code.MemberErrorCode.*;

import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.security.encoder.application.port.EncodePasswordPortIn;
import com.gomo.app.common.security.encoder.application.port.VerifyPasswordPortIn;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.model.Password;
import com.gomo.app.core.member.domain.service.MemberService;
import com.gomo.app.core.member.exception.MemberAuthenticationFailedException;
import com.gomo.app.support.logging.AuditLog;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
public class UpdatePasswordUseCase {

	private final VerifyPasswordPortIn verifyPasswordPortIn;
	private final EncodePasswordPortIn encodePasswordPortIn;
	private final MemberService memberService;

	@AuditLog(action = "UPDATE_PASSWORD")
	public void update(UUID memberId, String originPassword, String newPassword) {
		Member member = memberService.find(memberId);
		ensureCorrectPassword(member, originPassword);
		String encoded = encodePasswordPortIn.encode(Password.ofRaw(newPassword).getPassword());
		member.updatePassword(Password.ofEncoded(encoded));
	}

	private void ensureCorrectPassword(Member member, String originPassword) {
		if (!verifyPasswordPortIn.matches(Password.ofRaw(originPassword).getPassword(), member.password())) {
			throw new MemberAuthenticationFailedException(AUTHENTICATION_FAILED);
		}
	}
}
