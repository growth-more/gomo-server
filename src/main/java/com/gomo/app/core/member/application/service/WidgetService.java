package com.gomo.app.core.member.application.service;

import java.util.UUID;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.core.member.application.port.in.WidgetUpdater;
import com.gomo.app.core.member.domain.model.Member;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class WidgetService implements WidgetUpdater {

	private final MemberService memberService;

	@Override
	@AuditLog(action = "WIDGET_UPDATE")
	public void update(UUID memberId, String snapshot) {
		Member member = memberService.findById(memberId);
		member.updateWidget(snapshot);
	}
}
