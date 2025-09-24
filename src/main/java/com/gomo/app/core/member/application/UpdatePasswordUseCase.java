package com.gomo.app.core.member.application;

import java.util.UUID;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.support.logging.AuditLog;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.model.MemberId;
import com.gomo.app.core.member.domain.model.Password;
import com.gomo.app.core.member.domain.service.MemberService;
import com.gomo.app.core.member.domain.service.PasswordService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdatePasswordUseCase {

	private final MemberService memberService;
	private final PasswordService passwordService;

	@AuditLog(action = "UPDATE_PASSWORD")
	public void update(UUID memberId, String originPassword, String newPassword) {
		Member member = memberService.find(MemberId.of(memberId));
		Password rawOld = Password.ofRaw(originPassword);
		Password rawNew = Password.ofRaw(newPassword);
		passwordService.matches(member.getPassword().getPassword(), rawOld.getPassword());
		member.updatePassword(rawNew, passwordService);
	}
}
