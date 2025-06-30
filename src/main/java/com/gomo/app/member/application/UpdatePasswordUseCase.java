package com.gomo.app.member.application;

import java.util.UUID;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.member.domain.model.Member;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.model.Password;
import com.gomo.app.member.domain.service.MemberService;
import com.gomo.app.member.domain.service.PasswordService;
import com.gomo.app.member.presentation.request.UpdatePasswordRequest;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdatePasswordUseCase {

	private final MemberService memberService;
	private final PasswordService passwordService;

	public void update(UUID memberId, UpdatePasswordRequest request) {
		Member member = memberService.find(MemberId.of(memberId));

		Password rawOld = Password.ofRaw(request.getOriginPassword());
		Password rawNew = Password.ofRaw(request.getUpdatedPassword());

		passwordService.matches(member.getPassword().getPassword(), rawOld.getPassword());
		member.updatePassword(rawNew, passwordService);
	}
}
