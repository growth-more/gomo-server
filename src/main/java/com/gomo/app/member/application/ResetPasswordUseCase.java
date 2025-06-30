package com.gomo.app.member.application;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.member.domain.model.Email;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.Password;
import com.gomo.app.member.domain.service.MemberService;
import com.gomo.app.member.domain.service.PasswordService;
import com.gomo.app.member.presentation.request.ResetPasswordRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class ResetPasswordUseCase {

	private final MemberService memberService;
	private final PasswordService passwordService;

	public void reset(ResetPasswordRequest request) {
		Member member = memberService.findByEmail(Email.of(request.getEmail()));

		Password rawNew = Password.ofRaw(request.getResetPassword());

		member.updatePassword(rawNew, passwordService);
	}
}
