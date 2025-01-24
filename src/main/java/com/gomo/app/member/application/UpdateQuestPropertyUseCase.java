package com.gomo.app.member.application;

import org.springframework.transaction.annotation.Transactional;

import com.gomo.app.common.application.ApplicationService;
import com.gomo.app.member.domain.repository.MemberRepository;
import com.gomo.app.member.presentation.request.UpdateQuestPropertyRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
@Transactional
public class UpdateQuestPropertyUseCase {

	private final MemberRepository memberRepository;

	public void update(UpdateQuestPropertyRequest request) {

	}
}
