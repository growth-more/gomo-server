package com.gomo.app.core.member.application.usecase;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.member.application.port.OAuthLoginMemberPortIn;
import com.gomo.app.core.member.domain.model.Email;
import com.gomo.app.core.member.domain.repository.MemberRepository;
import com.gomo.app.support.logging.AuditLog;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@ApplicationService
@Transactional
class OAuthLoginMemberUseCase implements OAuthLoginMemberPortIn {

	private final MemberRepository memberRepository;

	@AuditLog(action = "OAUTH_AUTHENTICATE_MEMBER")
	@Override
	public Optional<UUID> oauthAuthenticate(String email) {
		return memberRepository.findByEmail(Email.of(email)).map(
			member -> {
				member.validateActive();
				member.updateLastLoginDateTime(LocalDateTime.now());
				return member.id();
			}
		);
	}
}
