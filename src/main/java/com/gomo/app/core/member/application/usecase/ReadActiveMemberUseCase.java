package com.gomo.app.core.member.application.usecase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import org.springframework.data.domain.PageRequest;

import com.gomo.app.common.arch.ApplicationService;
import com.gomo.app.core.member.application.port.ReadActiveMemberPortIn;
import com.gomo.app.core.member.application.port.dto.ActiveMemberDto;
import com.gomo.app.core.member.domain.model.ActivateStatus;
import com.gomo.app.core.member.domain.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
class ReadActiveMemberUseCase implements ReadActiveMemberPortIn {

	private final MemberRepository memberRepository;

	@Override
	public List<ActiveMemberDto> findAll(LocalDate loginCutoffDate) {
		return memberRepository.findByActivateStatusAndLastLoginDateTimeGreaterThanEqual(
				ActivateStatus.ACTIVE,
				LocalDateTime.of(loginCutoffDate, LocalTime.MIDNIGHT),
				// TODO [2025-10-14] jhl221123 : 수정 필요
				PageRequest.of(0, 10000)
			).stream()
			.map(ActiveMemberDto::from)
			.toList();
	}
}
