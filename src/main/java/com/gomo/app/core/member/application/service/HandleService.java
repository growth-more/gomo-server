package com.gomo.app.core.member.application.service;

import java.util.UUID;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.common.logging.AuditLog;
import com.gomo.app.core.member.application.port.in.HandleUpdater;
import com.gomo.app.core.member.application.port.in.HandleValidator;
import com.gomo.app.core.member.domain.exception.HandleDuplicatedException;
import com.gomo.app.core.member.domain.exception.code.HandleErrorCode;
import com.gomo.app.core.member.domain.model.Handle;
import com.gomo.app.core.member.domain.model.Member;
import com.gomo.app.core.member.domain.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Transactional
@ApplicationService
class HandleService implements HandleUpdater, HandleValidator {

	private final MemberService memberService;
	private final MemberRepository memberRepository;

	@Override
	@AuditLog(action = "UPDATE_HANDLE")
	public void update(UUID memberId, String handle) {
		Member member = memberService.findById(memberId);
		validateDuplicated(handle);
		member.updateHandle(handle);
	}

	@Override
	@Transactional(readOnly = true)
	public void validateDuplicated(String handle) {
		memberRepository.findByHandle(Handle.of(handle)).ifPresent(m -> {
			throw new HandleDuplicatedException(HandleErrorCode.DUPLICATED);
		});
	}
}
