package com.gomo.app.member.application;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.member.domain.model.Email;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.Password;
import com.gomo.app.member.domain.service.MemberService;
import com.gomo.app.member.domain.service.PasswordService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class ResetPasswordUseCase {

	private final MemberService memberService;
	private final PasswordService passwordService;

	public void reset(String email, String newPassword) {
		Member member = memberService.findByEmail(Email.of(email));
		Password rawNew = Password.ofRaw(newPassword);
		member.updatePassword(rawNew, passwordService);
	}
}
