package com.gomo.app.core.member.application.service;

import java.time.LocalDateTime;
import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.core.member.application.port.in.MemberLoginProcessor;
import com.gomo.app.core.member.domain.model.Member;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class MemberLoginService implements MemberLoginProcessor {

	private final PasswordService passwordService;
	private final MemberService memberService;

	@Override
	@AuditLog(action = "AUTHENTICATE_MEMBER")
	public UUID login(String email, String password) {
		Member member = memberService.findByEmail(email);
		passwordService.verify(member, password);
		member.validateActive();
		member.validateLoginProviderIsEmail();
		member.updateLastLoginDateTime(LocalDateTime.now());
		return member.getId();
	}
}
