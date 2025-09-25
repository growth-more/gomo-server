package com.gomo.app.core.member.application.usecase;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.jwt.port.VerifyJwtPortIn;
import com.gomo.app.core.member.application.port.EncodePasswordPortOut;
import com.gomo.app.core.member.domain.model.Email;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.model.Password;
import com.gomo.app.core.member.domain.service.MemberService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class ResetPasswordUseCase {

	private final VerifyJwtPortIn verifyJwtPortIn;
	private final MemberService memberService;
	private final EncodePasswordPortOut encodePasswordPortOut;

	public void reset(String email, String newPassword, String temporaryToken) {
		verifyJwtPortIn.validateToken(temporaryToken);
		Member member = memberService.findByEmail(Email.of(email));
		String encoded = encodePasswordPortOut.encode(Password.ofRaw(newPassword).getPassword());
		member.updatePassword(Password.ofEncoded(encoded));
	}
}
