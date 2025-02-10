package com.gomo.app.common.domain;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import com.gomo.app.common.authentication.MemberContext;
import com.gomo.app.common.authentication.SessionMember;

@Component
public class AuditorAwareImpl implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {
		SessionMember sessionMember = MemberContext.getSessionMember();
		if (sessionMember == null || sessionMember.getId() == null) {
			return Optional.of("SYSTEM");
		}

		return Optional.ofNullable(sessionMember.getId().toString());
	}
}
