package com.gomo.app.member.application.usecase;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import com.gomo.app.common.ApplicationService;
import com.gomo.app.member.application.port.ReadActiveMemberPortIn;
import com.gomo.app.member.application.port.dto.ActiveMemberDto;
import com.gomo.app.member.domain.model.ActivateStatus;
import com.gomo.app.member.domain.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
class ReadActiveMemberUseCase implements ReadActiveMemberPortIn {

	private final MemberRepository memberRepository;

	@Override
	public List<ActiveMemberDto> findAll(LocalDate lastLoginDate) {
		return memberRepository.findByActivateStatusAndLastLoginDateTimeGreaterThanEqual(
				ActivateStatus.ACTIVE,
				LocalDateTime.of(lastLoginDate, LocalTime.MIDNIGHT)
			).stream()
			.map(ActiveMemberDto::from)
			.toList();
	}
}
