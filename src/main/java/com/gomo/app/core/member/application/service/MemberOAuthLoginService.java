package com.gomo.app.core.member.application.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.core.member.application.port.in.MemberOAuthLoginProcessor;
import com.gomo.app.core.member.domain.model.Email;
import com.gomo.app.core.member.domain.repository.MemberRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class MemberOAuthLoginService implements MemberOAuthLoginProcessor {

	private final MemberRepository memberRepository;

	@Override
	@AuditLog(action = "OAUTH_AUTHENTICATE_MEMBER")
	public Optional<UUID> login(String email) {
		return memberRepository.findByEmail(Email.of(email)).map(
			member -> {
				member.validateActive();
				member.updateLastLoginDateTime(LocalDateTime.now());
				return member.getId();
			}
		);
	}
}
