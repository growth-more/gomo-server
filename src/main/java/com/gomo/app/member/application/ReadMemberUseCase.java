package com.gomo.app.member.application;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.member.domain.model.MemberId;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.presentation.response.ReadMemberResponse;
import com.gomo.app.point.domain.service.PointService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class ReadMemberUseCase {

	private final PointService pointService;
	private final MemberRepository memberRepository;

	public ReadMemberResponse find(MemberId memberId) {
		return null;
	}
}
